(ns my-exercise.search
  (:require [hiccup.page :as html]
            [org.httpkit.client :as http]
            [clojure.edn :as edn]
            [clojure.string :as string]
            [my-exercise.specs :as specs]
            [clojure.spec.alpha :as s]))

(def base-url "https://api.turbovote.org/elections/upcoming?district-divisions=")

(defn division-state
  "Build an OCD-ID given a state."
  [state]
  (str "ocd-division/country:us/state:" state))

(defn division-place
  "Build an OCD-ID given a state and a place."
  [state place]
  (str "ocd-division/country:us/state:" state "/place:" place))

(defn build-query
  "Build a url for querying turbovote. Given the validated params, figures out
   the largest number of matching division identifiers and assembles them
   together."
  [{:keys [state place] :as validated-params}]
  (let [state (string/lower-case state)]
    (str base-url
         (division-state state)
         (when place (str "," (division-place state place))))))

(defn validate
  "Ensure that state is present. If it is, lower-case it, otherwise return nil."
  [{:keys [state] :as params}]
  (when (s/valid? ::specs/state (string/lower-case state))
    (update params :state string/lower-case)))

;; TODO handle fetch failure
(defn fetch-data
  "Fetch data from turbovote."
  [url]
  (let [{:keys [status headers body error] :as resp} @(http/get url {:as :text})]
    (when (not error) (edn/read-string body))))

(defn lookup-elections [params]
  (some-> params
          validate
          build-query
          fetch-data))

;; TODO add helpful error message for invalid params
(defn search-handler [{:keys [street street-2 city state zip] :as params}]
  (if-let [elections (lookup-elections params)]
    (html/html5
     [:div
      [:p (if (> (count elections) 1)
            "Here are some upcoming elections in your area:"
            "There's an upcoming election in your area:")]
      [:ul
       (for [election elections]
         [:li (:description election)])]])))
