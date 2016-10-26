(ns day8.re-frame.effectful.kv-store-test
  (:require [cljs.test :refer-macros [is deftest testing use-fixtures]]
            [day8.re-frame.effectful.kv-store :as kv-store]))

(def ^:private -store (atom {}))
(def ^:private store
  (reify kv-store/KVStore
    (get-key [_ n] (nth (keys @-store) n))
    (get-item [_ k] (get @-store k))
    (set-item [_ k v] (swap! -store assoc k v))
    (remove-item [_ k] (swap! -store dissoc k))
    (clear [_] (reset! -store {}))))

(defn kv-store-fixture []
  {:before #(reset! -store {})})

(use-fixtures :each (kv-store-fixture))

(deftest kv-store-fx
  (testing "set-item"
    (kv-store/kv-store-fx
     {:store  store
      :method :set-item
      :key    :test-k
      :value  :test-v})
    (is (= :test-v (get @-store :test-k))))

  (testing "remove-item"
    (kv-store/kv-store-fx
     {:store  store
      :method :remove-item
      :key    :test-k})
    (is (nil? (get @-store :test-k))))

  (testing "clear"
    (swap! -store assoc :test-k :test-v)
    (swap! -store assoc :test-k2 :test-v2)
    (kv-store/kv-store-fx
     {:store  store
      :method :clear})
    (is (= 0 (count (keys @-store))))))

(deftest kv-store-cofx
  (swap! -store assoc :test-k :test-v)

  (testing "get-item"
    (let [cofx (kv-store/kv-store-cofx
                {}
                {:store  store
                 :method :get-item
                 :key    :test-k})]
      (is (= (get-in cofx [:kv-store/value :test-k]) :test-v))))

  (testing "get-key"
    (let [cofx (kv-store/kv-store-cofx
                {}
                {:store  store
                 :method :get-key
                 :index  0})]
      (is (= (get-in cofx [:kv-store/value 0]) :test-k)))))
