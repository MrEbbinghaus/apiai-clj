# apiai-clj
[![Clojars Project](https://img.shields.io/clojars/v/apiai-clj/apiai.svg)](https://clojars.org/apiai-clj/apiai)


A Clojure library designed to handle api.ai POST requests.

## Usage

### Example in a compojure app.
Note that the JSON in the request is parsed to a clojure map. Keys are parsed to keywords.
Same for the other way round.

#### `dispatch-action`:
```clojure
(ns apiai-clj.example-app
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.middleware.json :refer [wrap-json-response wrap-json-body]]
            [ring.util.response :refer [response]]
            [apiai-clj.core :as ai]
            [apiai-clj.example-actions :refer :all]))

(defroutes app-routes
  (GET "/" [] "Hello World")
  (POST "/" request (response (ai/dispatch-action (:body request))))
  (route/not-found "Not Found"))


(def app
  (-> app-routes
      (wrap-defaults site-defaults)
      (wrap-json-response)
      (wrap-json-body {:keywords? true :pretty? true})))

```

#### `defaction`:

Defines an action complementary to an action on api.ai

```clojure
(ns apiai-clj.example-actions
  (:require [apiai-clj.core :refer :all]))

;; For an apiai-action with name `hello-world`, without any parameter
(defaction hello-world [_] (simple-speech-response "Hello World"))

;; For an apiai-action with name `echo-name` with parameter `given-name`
(defaction echo-name [request-body]
  (let [given-name (get-in request-body [:result :parameters :given-name])]
    (simple-speech-response "Hello " given-name)))
```

## License

Copyright © 2017 Björn Ebbinghaus

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
