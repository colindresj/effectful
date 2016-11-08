(ns day8.re-frame.effectful.random
  (:require [clojure.set :as set]
            [re-frame.core :refer [reg-fx reg-cofx]]))

;; Create random-int-param spec
(defn random-int
  "ADD DOC"
  [cofx n]
  {:pre  [(s/valid? ::random-int opts)]}
  {:post [(integer? %)]}
  (if (integer? n)
    (rand-int n)
    (let [[down up] (:range opts)]
      (-> up (- down) rand-int (+ down)))))

(defn random-uuid [cofx]
  (random-uuid))

(defn random-bool
  "ADD DOC"
  [cofx likelihood] ;; Map or single param?
  {:post [(boolean? %)]}
  (let [likelihood (or likelihood 50)]
    (-> (rand)
        (* 100)
        (< likelihood))))

;; Look at chancejs

;; Create random-float-param spec
; (defn random-float
;   "ADD DOC"
;   ([cofx] (rand))
;   ([cofx f]
;   {:pre  [(s/valid? ::random-float opts)]}
;   {:post [(float? %)]}
;   (if (float? f)
;     (rand f)
;     (let [[down up] (:range opts)]
;       (-> up (- down) rand (+ down))))))
