(ns notebook.notes
  (:require [clojure.math.numeric-tower :as math]
            [notebook.math :as m]))

;; Assumes a chromatic scale

(def midi-nums (range 0 128))
(def pitch-classes [[:C] [:C# :Db] [:D] [:D# :Eb] [:E] [:F]
                    [:F# :Gb]
                    [:G] [:G# :Ab] [:A] [:A# :Bb] [:B]])
(def num-notes-in-octave (count pitch-classes))

(defn transpose-pitch-class [n pitch-class]
  (assert (some #(some #{pitch-class} %) pitch-classes))
  (let [pitch-class-cycle (cycle pitch-classes)
        start (drop-while #(not (some #{pitch-class} %)) pitch-class-cycle)]
    (first (drop n start))))

(defn midi-num->pitch-class [midi-num]
  (-> (mod midi-num num-notes-in-octave)
      pitch-classes
      first))

(defn midi-num->octave [midi-num]
  (-> midi-num
      (quot num-notes-in-octave)
      dec))

;; 440 and 69 are hardcoded - remove.
(defn midi-num->frequency [midi-num]
  (* (math/expt 2 (/ (- midi-num 69) 12)) 440))

(defn frequency->midi-num [frequency]
  (math/round (+ (* 12 (m/log2 (/ frequency 440))) 69)))

(defn midi-num->note [midi-num]
  (let [pitch-class (midi-num->pitch-class midi-num)
        octave (midi-num->octave midi-num)]
    {:midi-num midi-num
     :frequency (midi-num->frequency midi-num)
     :pitch-class pitch-class
     :octave octave
     :name (str (name pitch-class) octave)}))

(def notes (mapv midi-num->note midi-nums))

;; Compute rather than scan for name
(defn name->note [name]
  (first (filter #(= name (:name %)) notes)))

(defn note-seq [note]
  ;; Should limite to max midi-num
  (iterate #(midi-num->note (inc (:midi-num %))) note))