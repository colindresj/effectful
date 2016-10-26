(ns day8.re-frame.effectful.web-storage
  (:require [day8.re-frame.effectful.kv-store :as kv-store]
            [re-frame.core :refer [reg-fx reg-cofx]]))

(def ^:private local-storage
  (reify kv-store/KVStore
    (get-key [_ n] (.key js/localStorage n))
    (get-item [_ k] (.getItem js/localStorage k))
    (set-item [_ k v] (.setItem js/localStorage k v))
    (remove-item [_ k] (.removeItem js/localStorage k))
    (clear [_] (.clear js/localStorage))))

(def ^:private session-storage
  (reify kv-store/KVStore
    (get-key [_ n] (.key js/sessionStorage n))
    (get-item [_ k] (.getItem js/sessionStorage k))
    (set-item [_ k v] (.setItem js/sessionStorage k v))
    (remove-item [_ k] (.removeItem js/sessionStorage k))
    (clear [_] (.clear js/sessionStorage))))

(defn local-storage-fx [m]
  (kv-store/kv-store-fx
   (merge m {:store local-storage})))

(defn local-storage-cofx [cofx m]
  (kv-store/kv-store-cofx
   cofx
   (merge m {:store local-storage})))

(defn session-storage-fx [m]
  (kv-store/kv-store-fx
   (merge m {:store session-storage})))

(defn session-storage-cofx [cofx m]
  (kv-store/kv-store-cofx
   cofx
   (merge m {:store session-storage})))

(reg-fx :local-storage local-storage-fx)
(reg-cofx :local-storage local-storage-cofx)
(reg-fx :session-storage session-storage-fx)
(reg-cofx :session-storage session-storage-cofx)
