
#FROM openjdk:11
#
#COPY target/spring_rest_docker.jar app.jar
#
#ENTRYPOINT ["java", "-jar", "/app.jar"]

FROM maven:3.8.1-jdk-11-openj9 as builder

WORKDIR /app
COPY pom.xml .
RUN mvn clean

COPY src ./src
RUN mvn package -Dmaven.test.skip

FROM adoptopenjdk/openjdk11

COPY --from=builder /app/target/spring_rest_docker.jar /spring_rest_docker.jar

ENTRYPOINT ["java","-jar","spring_rest_docker.jar"]
  ## Run the web service on container startup.
CMD ["java","-Dcom.sun.net.ssl.checkRevocation=false","-Dserver.port=9090","-jar","spring_rest_docker.jar"]

ARG JAR_FILE=target/.jar





