#!/bin/sh

kubectl create -f deploy-postgresql.yaml 
kubectl wait --for=condition=Ready pod --all --timeout=30m

kubectl create -f deploy-svc.yaml
