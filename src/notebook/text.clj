(ns notebook.text
  (:require [notebook.math :as math]))

(defn format-center [ch pad fmt & args]
  (let [s (apply format fmt args)
        p (/ (- pad (count s)) 2)
        pre-pad (apply str (-> p math/floor (repeat ch)))
        post-pad (apply str (-> p math/ceil (repeat ch)))]
    (str pre-pad s post-pad)))
