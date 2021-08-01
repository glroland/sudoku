FROM registry.access.redhat.com/ubi8/openjdk-11-runtime:latest
EXPOSE 8080
WORKDIR /deployments
COPY target/sudoku-svc-1.0.jar /deployments
ENTRYPOINT ["java","-XX:UseAVX=0","-jar","sudoku-svc-1.0.jar"]

