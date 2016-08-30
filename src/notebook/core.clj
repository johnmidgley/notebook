(ns notebook.core
  (:require [notebook.notes :as notes]))

(defn note-state [note context]
  {:note note
   :context context})



