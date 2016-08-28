(ns notebook.math)

(defn log [b n]
  (/ (Math/log n) (Math/log b)))

(def log2 (partial log 2))