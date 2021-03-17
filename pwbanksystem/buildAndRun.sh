#!/bin/sh
mvn clean package && docker build -t it.tss/pwbanksystem .
docker rm -f pwbanksystem || true && docker run -d -p 8080:8080 -p 4848:4848 --name pwbanksystem it.tss/pwbanksystem 
