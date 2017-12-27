(ns my-exercise.specs
  (:require [clojure.spec.alpha :as s]
            [clojure.string :as string]
            [my-exercise.us-state :as us-state]))

(s/def ::state (set (map string/lower-case us-state/postal-abbreviations)))
(s/def ::place string?)
(s/def ::div-state (s/keys :req-un [::state] ))
(s/def ::div-place (s/keys :req-un [::state ::place]))
