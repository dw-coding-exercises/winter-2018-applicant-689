(ns my-exercise.search-test
  (:require [my-exercise.search :as search]
            [my-exercise.specs :as specs]
            [clojure.test :refer :all]
            [clojure.spec.alpha :as s]))

(deftest validation-test
  (testing "only valid state abbreviations are allowed."
    (is (s/valid? ::specs/div-state)
        (search/validate {:state "WI"}))))
