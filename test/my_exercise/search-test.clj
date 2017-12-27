(ns my-exercise.search-test
  (:require [my-exercise.search :as search]
            [my-exercise.specs :as specs]
            [clojure.test :refer :all]
            [clojure.spec.alpha :as s]))

;; TODO add fn-specs and test with generated data


;; When I run `lein test` this complains, but I didn't have time to fix it.
(deftest validation-test
  (testing "only valid state abbreviations are allowed."
    (println [::specs/div-state (search/validate {:state "WI"})])
    (is (apply s/valid? [::specs/div-state (search/validate {:state "WI"})]))))
