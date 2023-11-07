
FROM openjdk:11-jre-slim

WORKDIR /app

COPY target/spring_rest_docker.jar /app

EXPOSE 9090

CMD ["java", "-jar", "spring_rest_docker.jar"]






