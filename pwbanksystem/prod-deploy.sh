#!/bin/sh


mvn clean package  -P prod && docker build --rm -t dhtssdev/pwbanksystem .

docker push dhtssdev/pwbanksystem