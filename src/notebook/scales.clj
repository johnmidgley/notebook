(ns notebook.scales
  (:require [notebook.notes :as notes]))

(defn integer [v]
  (when (integer? v) v))

(defn to-int-interval [i]
  (or (integer i) ({:S 1 :T 2 :W 2 :H 1} i)))

(defn ->scale [pitch-class intervals]
  (let [int-intervals (mapv to-int-interval intervals)]
    (assert ((set (flatten notes/pitch-classes)) pitch-class) "Invalid pitch class")
    (assert (= notes/num-notes-in-octave (apply + int-intervals)) "Intervals must cover an octave")
    {:root pitch-class
     :intervals int-intervals}))

(defn mode [n scale]
  (assert (and (>= n 0) (< n (count (:intervals scale)))))
  (let [{:keys [root intervals]} scale
        transposition-interval (apply + (take n intervals))
        interval-cycle (cycle intervals)]
    (->scale
      (first (notes/transpose-pitch-class transposition-interval root))
      (take (count intervals)
            (drop n interval-cycle)))))

(def c-major (->scale :C [:T :T :S :T :T :T :S]))
