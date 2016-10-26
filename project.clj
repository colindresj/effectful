(defproject day8.re-frame/effectful "0.1.0-SNAPSHOT"
  :description "A collection of re-frame effects and co-effects"
  :url "https://github.com/colindresj/effectful.git"
  :license {:name "MIT"}

  :pedantic? :warn
  :dependencies [[org.clojure/clojure "1.9.0-alpha13"]
                 [org.clojure/clojurescript "1.9.293" :scope "provided"]
                 [re-frame "0.8.0"]]

  :plugins [[lein-cljsbuild "1.1.3"]]

  :source-paths ["src"]
  :test-paths ["test"]
  :clean-targets ^{:protect false} [:target-path]

  :cljsbuild {:builds
              [{:id "test"
                :source-paths ["src" "test"]
                :compiler {:output-to "out/test.js"
                           :main day8.re-frame.effectful.test-runner
                           :optimizations :none}}]}

  :doo {:build "test"}

  :profiles {:dev
             {:plugins [[lein-doo "0.1.7"]
                        [lein-ancient "0.6.10"]]}})
