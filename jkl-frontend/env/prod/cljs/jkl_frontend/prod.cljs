(ns jkl-frontend.prod
  (:require
    [jkl-frontend.core :as core]))

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))

(core/init!)
