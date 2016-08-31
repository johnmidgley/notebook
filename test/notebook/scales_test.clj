(ns notebook.scales-test
  (:require [clojure.test :refer :all]
            [notebook.scales :refer :all]))

(deftest test-modes
  (testing "Modes"
    (is (thrown? java.lang.AssertionError (mode 12 c-major)))
    (is (mode 5 c-major) (scale :A [2 1 2 2 1 2 2]))))



