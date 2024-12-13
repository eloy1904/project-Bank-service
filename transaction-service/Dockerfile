FROM openjdk:17-jdk-slim
WORKDIR /app
##COPY target/transaction-service.jar transaction-service.jar
##COPY target/*.jar app.jar
COPY ./target/transaction-service-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
