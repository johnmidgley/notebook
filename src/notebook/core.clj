(ns notebook.core)

(def notes (range 0 128))
(def labels [:C :C#/Db :D :D#/Eb :E :F :F#/Gb :G :G#/Ab :A :A#/Bb :B])
(def notes-in-octave (count labels))

(defn note->label [note]
  (labels (mod note notes-in-octave)))

