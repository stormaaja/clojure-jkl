(ns jkl-backend.handler
  (:require [compojure.core :refer [defroutes GET PUT OPTIONS]]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.middleware.cors :refer [wrap-cors]]))

(def app-data (atom {:visitor-count 0 :message ""}))

(defn home-page [data]
  (str data))

(defn add-visitor! []
  (swap! app-data update :visitor-count inc))

(defn set-message! [message]
  (swap! app-data assoc :message message))

(defroutes app-routes
  (GET "/" []
       (add-visitor!)
       (home-page @app-data))
  (PUT "/" [message]
        (set-message! message))
  (OPTIONS "/" [] "")
  (route/not-found "Not Found"))

(def app
  (wrap-reload
    (wrap-cors
      (wrap-defaults app-routes api-defaults)
      :access-control-allow-origin [#"http://localhost:3449"]
      :access-control-allow-credentials "true"
      :access-control-allow-methods [:get :put :post :delete])))
