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

(defn quick-reply-response
  "Returns a quick reply response for the facebook api.ai integration
  quick-replies has to be a sequence of 'quick_replie's
  https://developers.facebook.com/docs/messenger-platform/send-api-reference/quick-replies#quick_reply"
  [text quick-replies]
  {:pre [(s/valid? (s/coll-of ::quick-reply :min-count 1 :max-count 11) quick-replies)
         (s/valid? string? text)]}
  (response {:text text
             :quick_replies (vec quick-replies)}))

(defn simple-list-response
  "Returns a response for API.ai to display a simple list in the facebook integration.
  entrys has to be a sequence of 'element' objects
  https://developers.facebook.com/docs/messenger-platform/send-api-reference/list-template#element"
  [entrys]
  (response {:attachment {:type "template"
                          :payload
                          {:template_type "list"
                           :top_element_style "compact"
                           :elements (vec entrys)
                           :buttons [{:title "View More"
                                      :type "postback"
                                      :payload "more"}]}}}))

(defn text-quick-reply
  "Returns a quick_reply object of type 'text', with text and payload set"
  [text payload]
  {:content_type "text"
   :title text
   :payload payload})