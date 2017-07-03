(ns apiai.integrations.facebook
  "A library to work with the facebook api.ai integration"
  (:require [apiai.core :as core]
            [clojure.spec.alpha :as s]))

(defn response
  "Returns a response for api.ai to forward to the facebook integration.
  message has to be a 'message'-object
  https://developers.facebook.com/docs/messenger-platform/send-api-reference#message"
  [message]
  {:data {:facebook message}})

(defn text-response [text]
  (response {:text text}))

(defn quick-reply-response
  "Returns a quick reply response for the facebook api.ai integration
  quick-replies has to be a sequence of 'quick_reply's
  https://developers.facebook.com/docs/messenger-platform/send-api-reference/quick-replies#quick_reply"
  [text quick-replies]
  (response {:text text
             :quick_replies (vec quick-replies)}))

(defn simple-list-response
  "Returns a response for API.ai to display a simple list in the facebook integration.
  entrys has to be a sequence of 'element' objects
  https://developers.facebook.com/docs/messenger-platform/send-api-reference/list-template#element"
  [more? entrys]
  (let [r {:attachment {:type "template"
                        :payload
                              {:template_type "list"
                               :top_element_style "compact"
                               :elements (vec entrys)}}}]
    (response (if more? (assoc-in r [:attachment :payload :buttons]
                                  [{:title "View More"
                                    :type "postback"
                                    :payload "more"}])
                        r))))

(defn text-quick-reply
  "Returns a quick_reply object of type 'text', with text and payload set"
  [text payload]
  {:content_type "text"
   :title text
   :payload payload})