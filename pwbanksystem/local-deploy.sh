#!/bin/sh
mvn clean package -P local && docker build --rm -t dhtssdev/pwbanksystem .
docker rm -f pwbanksystem || true && docker run -d -p 8080:8080 -p 4848:4848 --name pwbanksystem --network db dhtssdev/pwbanksystem  && docker logs -f pwbanksystem
