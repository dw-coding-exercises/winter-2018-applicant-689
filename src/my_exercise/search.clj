(ns my-exercise.search
  (:require [hiccup.page :as html]
            [org.httpkit.client :as http]
            [clojure.edn :as edn]
            [clojure.string :as string]))

(def turbovote
  (str "https://api.turbovote.org/"
       "elections/upcoming"
       "?district-divisions="
       "ocd-division/country:us/state:or" ","
       "ocd-division/country:us/state:or/place:portland"))

(def base-url "https://api.turbovote.org/elections/upcoming?district-divisions=")

(defn division-state [state]
  (str "ocd-division/country:us/state:" state))

(defn division-place [state place]
  (str "ocd-division/country:us/state:" state "/place:" place))

(defn build-query [{:keys [state place] :as validated-params}]
  (let [state (string/lower-case state)]
    (str base-url
         (division-state state)
         (when place (str "," (division-place state place))))))

(defn validate
  "Ensure that state is present. If it is, lower-case it, otherwise return nil."
  [{:keys [state city] :as params}]
  (when (not= state "") (update params :state string/lower-case)))

;; TODO handle fetch failure
(defn fetch-data
  "Fetch data from turbovote."
  [url]
  (let [{:keys [status headers body error] :as resp} @(http/get url {:as :text})]
    (edn/read-string body)))

(defn lookup-elections [params]
  (some-> params
          validate
          build-query
          fetch-data))

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
