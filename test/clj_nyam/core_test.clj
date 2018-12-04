(ns clj-nyam.core-test
  (:require [clojure.test :refer :all]
            [clj-nyam.core :refer :all]
            [clojure.java.io :as io]))

(def temp-files-names "Set of test files names"
  (set ["clj-nyam-001.properties" "clj-nyam-002.properties" "clj-nyam-003.properties"]))

(def temp-files "List of test files full paths"
  (doall (map
          (fn [x] (str (System/getProperty "java.io.tmpdir") "/" x))
          temp-files-names)))

(def test-key "Test key alias" "test.key")
(def test-value-template "Test value template" "test.value")

(def test-resources-key "Test resources key" "test.resource.key")
(def test-resources-value "Test resources value" "test.resource.value")

(defn generate-test-kv "Generate test key-value pair"
  [i] (str test-key "=" test-value-template i))

;; write test key-values to test files
(doall
  (map-indexed (fn [index item] (spit item (generate-test-kv index)))
               temp-files))

(deftest nyam-test
  (testing "Filesystem properties read and merge test"
    (let [props (build-properties ["not-existed-resource-file" "test-resources.properties"] (concat  ["not-existed-filesystem-file"] temp-files))]
      ;; filesystem test
      (is (= (str test-value-template (- (count temp-files) 1))
             (.getProperty props test-key)))
      ;; resources test
      (is (= (.getProperty props test-resources-key) test-resources-value)))))

(defn delete-temp-files "Delete temp files"
  [] (doall (map (fn [x] (io/delete-file x true))
                 temp-files)))

(defn test-fixture "Test fixture" [f]
  ()
  (f)
  (delete-temp-files))

(use-fixtures :once test-fixture)
