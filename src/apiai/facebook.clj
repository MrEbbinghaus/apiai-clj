(ns apiai.facebook
  "A library to work with the facebook api.ai integration"
  (:require [apiai.core :as core]
            [clojure.tools.logging :as log]))

(defn response [message]
  "Returns a response for api.ai to forward to the facebook integration.
  message has to be a 'message'-object
  https://developers.facebook.com/docs/messenger-platform/send-api-reference#message"
  {:data {:facebook message}})

(defn quick-reply-response [text quick-replies]
  "Returns a quick reply response for the facebook api.ai integration
  quick-replies has to be a sequence of 'quick_replie's
  https://developers.facebook.com/docs/messenger-platform/send-api-reference/quick-replies#quick_reply"
  (response {:data {:facebook}
                   {:text text
                    :quick_replies (vec quick-replies)}}))

(defn simple-list-response [entrys]
  "Returns a response for API.ai to display a simple list in the facebook integration.
  entrys has to be a sequence of 'element' objects
  https://developers.facebook.com/docs/messenger-platform/send-api-reference/list-template#element"
  (response {:data {:facebook {:attachment {:type "template"
                                            :payload
                                            {:template_type "list"
                                             :top_element_style "compact"
                                             :elements (vec entrys)
                                             :buttons [{:title "View More"
                                                        :type "postback"
                                                        :payload "more"}]}}}}}))

(defn text-quick-reply [text payload]
  "Returns a quick_reply object of type 'text', with text and payload set"
  {:content_type "text"
   :title text
   :payload payload})