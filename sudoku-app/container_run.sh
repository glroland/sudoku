#!/bin/sh

envsubst < config.json.env > /tmp/config.json

nginx -g "daemon off;"
