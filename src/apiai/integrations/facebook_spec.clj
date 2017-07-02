(ns apiai.integrations.facebook-spec
  (:require [clojure.spec.alpha :as s]))

(s/def ::quick_reply (s/map-of keyword? string?))