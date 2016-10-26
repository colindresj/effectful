(ns day8.re-frame.effectful.web-storage-test
  (:require [cljs.test :refer-macros [is deftest testing use-fixtures]]
            [day8.re-frame.effectful.web-storage :as web-storage]))

(defn web-storage-fixture []
  {:before #(do
             (.clear js/localStorage)
             (.clear js/sessionStorage))})

(use-fixtures :each (web-storage-fixture))

(deftest local-storage-fx
  (testing "set-item"
    (web-storage/local-storage-fx
      {:method :set-item
       :key    :test-k
       :value  :test-v})
    (is (= ":test-v" (.getItem js/localStorage ":test-k"))))

  (testing "remove-item"
    (web-storage/local-storage-fx
      {:method :remove-item
       :key    :test-k})
    (is (nil? (.getItem js/localStorage ":test-k"))))

  (testing "clear"
    (.setItem js/localStorage ":test-k" ":test-v")
    (.setItem js/localStorage ":test-k2" ":test-v2")
    (web-storage/local-storage-fx
      {:method :clear})
    (is (= 0 (.-length js/localStorage)))))
