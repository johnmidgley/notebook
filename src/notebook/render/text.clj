(ns notebook.render.text
  (:require [notebook.text :as text]))

(defmulti render :instrument)

(defn render-fret [width note-state]
  (str (text/format-center '- width (-> note-state :note :name)) '|))

(defn render-string [s]
  (let [[nut & r] (:note-states s)]
    (apply str
           (-> nut :note :name)
           "||"
           (map (partial render-fret 5) r))))

(defmethod render :guitar [g]
  (let [strings (:strings g)]
    (apply str (interpose "\n" (map render-string strings)))))