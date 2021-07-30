#!/bin/sh

NAMESPACE=$1
if [ -z "$NAMESPACE" ]
then
      echo "Usage: create.sh <namespace>"
      exit
fi
echo "Using namespace: $NAMESPACE"

kubectl --namespace=$NAMESPACE create -f deploy-postgresql.yaml 
kubectl --namespace=$NAMESPACE wait --for=condition=Ready pod --all --timeout=30m

kubectl --namespace=$NAMESPACE create -f deploy-log-svc.yaml
kubectl --namespace=$NAMESPACE create -f deploy-svc.yaml
