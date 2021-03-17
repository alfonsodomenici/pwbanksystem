# Build
mvn clean package && docker build -t it.tss/pwbanksystem .

# RUN

docker rm -f pwbanksystem || true && docker run -d -p 8080:8080 -p 4848:4848 --name pwbanksystem it.tss/pwbanksystem 

# System Test

Switch to the "-st" module and perform:

mvn compile failsafe:integration-test