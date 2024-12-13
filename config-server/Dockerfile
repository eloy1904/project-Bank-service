FROM openjdk:17-jdk-slim
WORKDIR /app
##COPY target/config-server.jar config-server.jar
##COPY target/*.jar app.jar
COPY ./target/config-server-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8888
ENTRYPOINT ["java", "-jar", "/app.jar"]