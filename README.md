# clj-nyam

[![Clojars Project](https://img.shields.io/clojars/v/org.clojars.mkarimov/clj-nyam.svg)](https://clojars.org/org.clojars.mkarimov/clj-nyam)

Simple clojure library, that loads java.util.Properties from classpath and filesystem files.

Inspired by [Maailma](https://github.com/metosin/maailma).

## Examples

```clj
(ns backend.system
  (:require [clj-nyam.core :as n]))

(def system-properties
  (n/build-properties ["resouces01.properties" "resouces02.properties"] ["fs01.properties" "fs02.properties"]))
```
## License

Copyright © 2018 [Marat Karimov](https://github.com/MaratKarimov).

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
