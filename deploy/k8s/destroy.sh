#!/bin/sh

NAMESPACE=$1
if [ -z "$NAMESPACE" ]
then
      echo "Usage: destroy.sh <namespace>"
      exit
fi
echo "Using namespace: $NAMESPACE"

kubectl --namespace=$NAMESPACE delete configmap svc-config --cascade='foreground'
kubectl --namespace=$NAMESPACE delete deployment svc --cascade='foreground'
kubectl --namespace=$NAMESPACE delete service svc --cascade='foreground'

kubectl --namespace=$NAMESPACE delete configmap log-svc-config --cascade='foreground'
kubectl --namespace=$NAMESPACE delete deployment log-svc --cascade='foreground'
kubectl --namespace=$NAMESPACE delete service log-svc --cascade='foreground'

kubectl --namespace=$NAMESPACE delete deployment postgresql --cascade='foreground'
kubectl --namespace=$NAMESPACE delete secret postgresql --cascade='foreground'
kubectl --namespace=$NAMESPACE delete service postgresql --cascade='foreground'
