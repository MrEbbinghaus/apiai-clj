(ns apiai.integrations.agent)

(defn simple-speech-response
  "Returns a valid api.ai map with 'speech' and 'displayText' set to speech."
  [& speech]
  {:speech (apply str speech)
   :displayText (apply str speech)
   :data {}
   :contextOut []
   :source ""})