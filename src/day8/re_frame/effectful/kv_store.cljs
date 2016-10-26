(ns day8.re-frame.effectful.kv-store
  (:require [cljs.spec :as s]
            [re-frame.core :refer [reg-fx reg-cofx]]))

(defprotocol KVStore
  "ADD DOC"
  (get-key [_ n])
  (get-item [_ k])
  (set-item [_ k v])
  (remove-item [_ k])
  (clear [_]))

(defn- stringify-kv [k v]
  [(str k) (str v)])

(defn kv-store-fx
  "ADD DOC"
  [{:keys [store method key value]}]
  {:pre [(satisfies? KVStore store)
         (s/valid? #{:set-item :remove-item :clear} method)]}
  (case method
    :set-item    (set-item store key value)
    :remove-item (remove-item store key)
    :clear       (clear store)))

(defn kv-store-cofx
  "ADD DOC"
  [cofx {:keys [store method key index]}]
  {:pre [(satisfies? KVStore store)
         (s/valid? #{:get-item :get-key} method)]}
  (let [result   (case method
                   :get-item (get-item store key)
                   :get-key  (get-key store index))
        key-path [:kv-store/value
                  (if (= method :get-item)
                    key
                    index)]]
    (assoc-in cofx key-path result)))

(reg-fx :kv-store kv-store-fx)
(reg-cofx :kv-store kv-store-cofx)
