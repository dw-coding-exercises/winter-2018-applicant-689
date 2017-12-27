(ns my-exercise.search
  (:require [hiccup.page :refer [html5]]))

(defn search-handler [{:keys [street street-2 city state zip] :as params}]
  (html5
   [:div (str "params: " params)
    [:ul
     [:li "Street: " street]
     [:li "Street 2: " street-2]
     [:li "City: " city]
     [:li "State: " state]
     [:li "Zip: " zip]]]))
