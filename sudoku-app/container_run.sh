#!/bin/sh

envsubst < config.json.env > config.json

nginx -g "daemon off;"
