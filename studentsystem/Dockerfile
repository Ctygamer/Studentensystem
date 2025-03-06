# Erster Schritt: Java-Anwendung mit Maven bauen
FROM maven:3.8.7-openjdk-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package

# Zweiter Schritt: Leichtgewichtigen Java-Container verwenden
FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=build /app/target/studentsystem-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
