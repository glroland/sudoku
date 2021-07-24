#!/bin/sh

kubectl delete configmap svc-config --cascade='foreground'
kubectl delete deployment svc --cascade='foreground'
kubectl delete service svc --cascade='foreground'

kubectl delete configmap log-svc-config --cascade='foreground'
kubectl delete deployment log-svc --cascade='foreground'
kubectl delete service log-svc --cascade='foreground'

kubectl delete deployment postgresql --cascade='foreground'
kubectl delete secret postgresql --cascade='foreground'
kubectl delete service postgresql --cascade='foreground'
