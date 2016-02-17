#!/bin/bash

curl -v 'http://localhost:9000/v1/api/iot/create' -H "Accept: application/json" -H "Content-type: application/json" -XPOST  -d '{"id":"3"}'
curl -v 'http://localhost:9000/v1/api/iot/findBy' -H "Accept: application/json" -H "Content-type: application/json" -XPOST  -d '{"id":"3"}'

