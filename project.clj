(defproject org.clojars.mkarimov/clj-nyam "0.3.0-RELEASE"
  :description "Simple properties reader"
  :url "https://github.com/MaratKarimov/clj-nyam"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.10.0"]]
  :deploy-repositories {"clojars-https" {:url "https://clojars.org/repo" :sign-releases false}}
  :profiles {:uberjar {:aot :all}})