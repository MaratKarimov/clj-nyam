(ns clj-nyam.core
  (:require [clojure.java.io :as io])
  (:require [clojure.spec.alpha :as spec]))

(def default-file-charset "Default charset for file parsing."
  (java.nio.charset.Charset/forName "UTF-8"))

(defn merge-properties "Merge key-values from one java.util.Properties instance to another"
  ([] (java.util.Properties.))
  ([x y] (doto (java.util.Properties. x) (.putAll y))))

(defn create-and-load-properties "Create new instance of java.util.Properties and load it from java.io.InputStreamReader."
  [isr] (doto (java.util.Properties.) (.load isr)))

(defn load-properties-from-filesystem "Reads configuration part from given path in filesystem."
  [file-path] (if (.exists (io/file file-path))
                (with-open [is (java.io.FileInputStream. file-path)
                            isr (java.io.InputStreamReader. is default-file-charset)]
                  (create-and-load-properties isr))
                (java.util.Properties.)))

(defn load-properties-from-classpath "Reads configuration part from given path in classpath."
 [file-path] (let [classpath-file-url (io/resource file-path)]
               (if (some? classpath-file-url)
                 (with-open [is (.openStream classpath-file-url)
                             isr (java.io.InputStreamReader. is default-file-charset)]
                   (create-and-load-properties isr))
                 (java.util.Properties.))))

(defn reduced-merge-properties "Merge key-values of java.util.Properties instances from different sources to one common instance"
  [file-paths source] (reduce merge-properties (map source file-paths)))

(defn merge-properties-filesystem "Merge key-values of java.util.Properties instances from filesystem files to one common instance"
  [file-paths] (reduced-merge-properties file-paths load-properties-from-filesystem))

(defn merge-properties-classpath "Merge key-values of java.util.Properties instances from classpath files to one common instance"
  [file-paths] (reduced-merge-properties file-paths load-properties-from-classpath))

(defn build-properties "Build propeties first from classpath files list, then from filesystem files"
  [list-of-classpath list-of-filesystem] (merge-properties
                                          (merge-properties-classpath list-of-classpath)
                                          (merge-properties-filesystem list-of-filesystem)))

;; spec for merge-properties function
(spec/fdef merge-properties
      :args (spec/cat
              :x (partial instance? java.util.Properties)
              :y (partial instance? java.util.Properties))
      :ret (partial instance? java.util.Properties))

;; spec for load-properties-from-filesystem function
(spec/fdef load-properties-from-filesystem
      :args (spec/cat
              :file-path string?
              :props (partial instance? java.util.Properties))
      :ret (partial instance? java.util.Properties))

;; spec for load-properties-from-classpath function
(spec/fdef load-properties-from-classpath
      :args (spec/cat
              :file-path string?)
      :ret (partial instance? java.util.Properties))
