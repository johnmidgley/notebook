(ns notebook.guitar
  (:require [notebook.core :as core]))

(defn fretted-string [num-notes open-note]
  {:name (:name open-note)
   :note-states
         (mapv #(hash-map :note % :˜state {:selected nil})
               (take num-notes (core/note-seq open-note)))})

;; Doesn't support unequal length strings
(defn guitar [open-notes num-notes-per-string]
  {:instrument :guitar
   :strings (mapv (partial fretted-string num-notes-per-string) open-notes)})

(def open-notes-standard-six-string ["E4" "D3" "G3" "D3" "A2" "E2"])

(def standard-six-string-guitar
  (guitar (map core/name->note open-notes-standard-six-string) 10))