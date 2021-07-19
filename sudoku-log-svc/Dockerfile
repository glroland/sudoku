FROM registry.access.redhat.com/ubi8/openjdk-11-runtime:latest
EXPOSE 8080
WORKDIR /deployments
COPY target/sudoku-log-svc-1.0.0-SNAPSHOT-runner.jar /deployments
ENTRYPOINT ["java","-jar","sudoku-log-svc-1.0.0-SNAPSHOT-runner.jar"]

