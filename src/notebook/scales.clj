(ns notebook.scales
  (:require [notebook.notes :as notes]))

(defn integer [v]
  (when (integer? v) v))

(defn to-int-interval [i]
  (or (integer i) ({:S 1 :T 2} i)))

(defn scale [pitch-class intervals]
  (let [int-intervals (mapv to-int-interval intervals)]
    (assert ((set (flatten notes/pitch-classes)) pitch-class) "Invalid pitch class")
    (assert (= notes/num-notes-in-octave (apply + int-intervals)))
    {:root pitch-class
     :intervals int-intervals}))


(def c-major (scale :C [:T :T :S :T :T :T :S])) 