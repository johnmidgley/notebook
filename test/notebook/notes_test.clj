(ns notebook.notes-test
  (:require [clojure.test :refer :all]
            [notebook.notes :refer :all]))

(deftest test-note-parameters
  (testing "Basic note parameters"
    (is (= (count midi-nums) 128))
    (is (= num-notes-in-octave 12))))

(deftest test-midi-nums
  (testing "MIDI numbers"
    (is (= (midi-num->pitch-class 0) :C))
    (is (= (midi-num->octave 0) -1))
    (is (= (frequency->midi-num 440) 69))))
