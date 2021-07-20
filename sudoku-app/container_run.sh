#!/bin/sh

envsubst < config.json.env > /tmp/config.json
envsubst < nginx.conf.env > /tmp/nginx.conf

nginx -g "daemon off;"
