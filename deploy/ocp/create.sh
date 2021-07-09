#!/bin/sh

oc create -f image-streams.yaml

oc create -f build-app.yaml 
oc create -f build-log-svc.yaml 
oc create -f build-ocr-svc.yaml 
oc create -f build-svc.yaml

oc create -f deploy-postgresql.yaml 

oc wait --for=condition=Complete build --all --timeout=30m

oc create -f deploy-app.yaml
oc create -f deploy-log-svc.yaml
oc create -f deploy-ocr-svc.yaml
oc create -f deploy-svc.yaml
