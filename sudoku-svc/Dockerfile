FROM registry.access.redhat.com/ubi9/openjdk-21-runtime:1.20-2.1721752928
EXPOSE 8080
WORKDIR /deployments
COPY target/sudoku-svc-1.0.jar /deployments
ENTRYPOINT ["java","-jar","sudoku-svc-1.0.jar"]
