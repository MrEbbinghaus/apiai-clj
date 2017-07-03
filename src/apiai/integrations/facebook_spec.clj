(ns apiai.integrations.facebook-spec
  (:require [clojure.spec.alpha :as s]
            [apiai.integrations.facebook :as fb]))

(s/def ::json-string-object (s/map-of keyword? string?))

(s/fdef fb/quick-reply-response
        :args (s/cat :text string?
                     :quick-replies (s/coll-of ::json-string-object :min-count 1 :max-count 11)))
