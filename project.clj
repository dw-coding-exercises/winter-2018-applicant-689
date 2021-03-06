(defproject my-exercise "0.1.0-SNAPSHOT"
  :description "An anonymous Democracy Works coding exercise"
  :min-lein-version "2.7.1"
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [ring "1.6.3"]
                 [ring/ring-defaults "0.3.1"]
                 [compojure "1.6.0"]
                 [hiccup "1.0.5"]
                 [http-kit "2.2.0"]
                 [org.clojure/test.check "0.9.0" :scope "test"]]
  :plugins [[lein-ring "0.12.1"]]
  :ring {:handler my-exercise.core/handler}
  :aliases {"submit" ["run" "-m" "my-exercise.submit"]})
