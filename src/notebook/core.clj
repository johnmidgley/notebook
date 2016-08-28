(ns notebook.core
  (:require [clojure.math.numeric-tower :as math]
            [notebook.math :as m]))

(def midi-nums (range 0 128))
(def pitch-classes [[:C] [:C# :Db] [:D] [:D# :Eb] [:E] [:F]
                    [:F# :Gb]
                    [:G] [:G# :Ab] [:A] [:A# :Bb] [:B]])
(def notes-in-octave (count pitch-classes))

(defn midi-num->pitch-class [midi-num]
  (-> (mod midi-num notes-in-octave)
      pitch-classes
      first))

(defn midi-num->octave [midi-num]
  (-> midi-num
      (quot notes-in-octave)
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

(defn note->midi-num [note]
  (:midi-num note))

(defn note->name [note]
  (:name note))

(def notes (mapv midi-num->note midi-nums))

;; Compute rather than scan for name
(defn name->note [name]
  (first (filter #(= name (note->name %)) notes)))

(defn note-context [note context]
  {:note note
   :context context})

(defn note-seq [note]
  (iterate #(midi-num->note (inc (note->midi-num %))) note))

(defn fretted-string [open-note num-notes]
  {:name          (note->name open-note)
   :note-contexts (vec
                    (map #(hash-map :note % :context {:selected nil})
                         (take num-notes (note-seq open-note))))})

(defn guitar []
  {:strings []})

