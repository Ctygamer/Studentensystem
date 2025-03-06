# Stage 1: Build the application
FROM maven:3.8.6-jdk-11 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests
 
# Stage 2: Create the final image
FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=builder /app/target/chat-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
