#!/bin/sh

oc delete deployment svc --cascade='foreground'
oc delete imagestream svc --cascade='foreground'
oc delete buildconfig svc --cascade='foreground'
oc delete service svc --cascade='foreground'
oc delete configmap svc-config --cascade='foreground'

oc delete deployment ocr-svc --cascade='foreground'
oc delete imagestream ocr-svc --cascade='foreground'
oc delete buildconfig ocr-svc --cascade='foreground'
oc delete service ocr-svc --cascade='foreground'

oc delete deployment log-svc --cascade='foreground'
oc delete imagestream log-svc --cascade='foreground'
oc delete buildconfig log-svc --cascade='foreground'
oc delete service log-svc --cascade='foreground'
oc delete configmap log-svc-config --cascade='foreground'

oc delete deployment app --cascade='foreground'
oc delete imagestream app --cascade='foreground'
oc delete buildconfig app --cascade='foreground'
oc delete route app --cascade='foreground'
oc delete service app --cascade='foreground'

oc delete service postgresql --cascade='foreground'
oc delete deploymentconfig postgresql --cascade='foreground'
oc delete secret postgresql --cascade='foreground'
