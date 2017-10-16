(ns ^:figwheel-no-load jkl-frontend.dev
  (:require
    [jkl-frontend.core :as core]
    [devtools.core :as devtools]))


(enable-console-print!)

(devtools/install!)

(core/init!)
