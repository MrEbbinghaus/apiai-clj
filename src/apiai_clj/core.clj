(ns apiai-clj.core)

(defmulti dispatch-action
          "Executes an action"
          #(get-in % [:result :action]))

(defmacro defaction
  "Creates and registers a new action. Name this action exactly like you did in api.ai!"
  [name params & body]
  `(defmethod dispatch-action ~(str name) ~params
     ~@body))

(defn simple-speech-response
  "Returns a valid api.ai map with 'speech' and 'displayText' set to speech."
  [& speech]
  {:speech (apply str speech)
   :displayText (apply str speech)
   :data []
   :contextOut []
   :source ""})

(defmethod dispatch-action :default [r] (simple-speech-response "Action: " (get-in r [:result :action]) " not implemented! Sorry."))