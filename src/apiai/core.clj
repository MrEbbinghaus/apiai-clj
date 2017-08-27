(ns apiai.core
  (:require [apiai.integrations.agent :as agent]
            [clj-http.client :as client]
            [clojure.data.json :as json]
            [clojure.string :refer [blank?]]))

(def apiai-base "https://api.api.ai/v1")
(def dev-token (System/getenv "API_AI_DEVTOKEN"))

(defmulti dispatch-action
          "Executes an action"
          #(get-in % [:result :action]))

(defmethod dispatch-action :default [r] (agent/simple-speech-response "Action: " (get-in r [:result :action]) " not implemented! Sorry."))

(defmacro defaction
  "Creates and registers a new action. Name this action exactly like you did in api.ai!"
  [name params & body]
  `(defmethod dispatch-action ~(str name) ~params
     ~@body))


(defn get-fulfillment
  "Gets the fulfillment part of the api.ai request or nil."
  [request] (get-in request [:result :fulfillment]))

(defn fulfillment-empty? [request]
  "True if speech is empty or there is no fulfillment"
  (blank? (get-in request [:result :fulfillment :speech])))

(defn request [body]
  {:headers {:Authorization [(str "Bearer" dev-token)]}
   :content-type :json
   :body (json/write-str body)})

(defn update-entity! [name entries]
  "Updates an entity on api.ai"
  (client/put (str apiai-base "/entities/" name "/entries") (request entries)))

(defn remove-entity! [name]
  "Remove an entity from api.ai.
   This won't work if the entity is in use in any intent."
  (client/delete (str apiai-base "/entities/" name) {:headers {:Authorization [(str "Bearer" dev-token)]}}))

(defn get-contexts [request]
  (into {} (map (fn [e] [(keyword (:name e)) e]) (get-in request [:result :contexts]))))

(defn get-service [request]
  (keyword (get-in request [:originalRequest :source]
             (get-in request [:result :source]))))
