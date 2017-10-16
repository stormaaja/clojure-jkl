(ns jkl-frontend.core
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require
    [cljs-http.client :as http]
    [cljs.core.async :refer [<!]]
    [reagent.core :as r]))

(def backend-url "http://localhost:3000")
(def app-state (r/atom {}))

(defn set-state! [state]
  (reset! app-state state))

(defn download-data [url]
  (go (let [response (<! (http/get url))]
    (set-state! (cljs.reader/read-string (:body response))))))

(defn put-message [url message]
  (go (let [response (<! (http/put url {:form-params {:message message}}))]
    (set-state! (cljs.reader/read-string (:body response))))))

(defn home-page []
  [:div [:h2 "Welcome to Reagent"]
   [:div "Message: "
    [:span (get @app-state :message "Ei saatavilla")]]
   [:div "Visitors: "
    [:span (get @app-state :visitor-count "Ei saatavilla")]]
   [:button {:on-click #(download-data backend-url)} "Reload"]])

(defn mount-root []
  (r/render [home-page] (.getElementById js/document "app")))

(defn init! []
  (mount-root)
  (download-data backend-url))
