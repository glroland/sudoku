#!/bin/sh

envsubst < config.json.env > /tmp/config.json
envsubst < nginx.conf.env > /opt/app-root/etc/nginx.default.d/nginx.conf

nginx -g "daemon off;"
