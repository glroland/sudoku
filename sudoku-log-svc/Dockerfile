FROM registry.access.redhat.com/ubi8/ubi:latest as build
RUN yum install -y wget
RUN yum install -y git
RUN yum install -y java-11-openjdk-devel
WORKDIR /deployments
RUN wget https://ftp.wayne.edu/apache/maven/maven-3/3.8.1/binaries/apache-maven-3.8.1-bin.tar.gz
RUN gzip -dc apache-maven-3.8.1-bin.tar.gz | tar xvf -
RUN git clone https://github.com/glroland/sudoku.git
WORKDIR /deployments/sudoku/sudoku-log-svc
RUN /deployments/apache-maven-3.8.1/bin/mvn clean package

FROM registry.access.redhat.com/ubi8/openjdk-11-runtime:latest
EXPOSE 8080
WORKDIR /deployments
COPY --from=build /deployments/sudoku/sudoku-log-svc/target/sudoku-log-svc-1.0.0-SNAPSHOT-runner.jar /deployments
ENTRYPOINT ["java","-jar","sudoku-log-svc-1.0.0-SNAPSHOT-runner.jar"]
