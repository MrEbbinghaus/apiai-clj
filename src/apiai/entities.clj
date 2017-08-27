(ns apiai.entities
  (:require [apiai.core :as core]
            [clj-http.client :as client]
            [clojure.data.json :as json]))

(def endpoint (str core/apiai-base "/entities"))

(defn- request
  ([f url] (request f url nil))
  ([f url body]
   (json/read-json (f (str endpoint url) {:headers      {:Authorization [(str "Bearer" core/dev-token)]}}
                           :content-type :json
                           :body (json/write-str body)))))

(defn get-entities []
  (request client/get endpoint))

(defn get-entity [eid]
  (request client/get (str endpoint "/" eid)))

(defn update-entities [entities]
  (request client/put endpoint entities))

(defn update-entity [eid entity]
  (request client/put (str "/" eid) entity))

(defn update-entries [eid entries]
  (request client/put (str "/" eid "/entries") entries))