FROM openjdk:17-jdk-slim
WORKDIR /app
#COPY target/account-service.jar account-service.jar
##COPY target/*.jar app.jar
COPY ./target/account-service-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
