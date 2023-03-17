FROM registry.access.redhat.com/ubi8/openjdk-17-runtime:latest
EXPOSE 8080
WORKDIR /deployments
COPY target/quarkus-app /deployments
ENTRYPOINT ["java","-jar","quarkus-run.jar"]
