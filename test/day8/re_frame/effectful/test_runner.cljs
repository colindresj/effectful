(ns day8.re-frame.effectful.test-runner
  (:require [doo.runner :refer-macros [doo-tests]]
            day8.re-frame.effectful.kv-store-test
            day8.re-frame.effectful.web-storage-test))

(doo-tests
  'day8.re-frame.effectful.kv-store-test
  'day8.re-frame.effectful.web-storage-test)
