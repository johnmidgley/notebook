(ns notebook.core
  (:require [clojure.math.numeric-tower :as math]))

(def midi-nums (range 0 128))
(def pitch-classes [[:C] [:C# :Db] [:D] [:D# :Eb] [:E] [:F]
                    [:F# :Gb]
                    [:G] [:G# :Ab] [:A] [:A# :Bb] [:B]])
(def notes-in-octave (count pitch-classes))

(defn midi-num->pitch-class [midi-num]
  (-> (mod midi-num notes-in-octave)
      pitch-classes
      first))

(defn note->octave [midi-num]
  (-> midi-num
      (quot notes-in-octave)
      dec))

(defn midi-num->frequency [midi-num]
  (* (math/expt 2 (/ (- midi-num 69) 12)) 440))

(defn midi-num->note [midi-num]
  (let [pitch-class (midi-num->pitch-class midi-num)
        octave (note->octave midi-num)]
    {:midi-num midi-num
     :frequency (midi-num->frequency midi-num)
     :pitch-class pitch-class
     :octave octave
     :name (str (name pitch-class) octave)}))

(def notes (mapv midi-num->note midi-nums))



