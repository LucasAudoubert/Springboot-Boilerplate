#FROM maven:3.9.16-eclipse-temurin-17 as build
#
#WORKDIR /app
#
#COPY src/ /app/src
#COPY pom.xml /app/pom.xml
#
#RUN mvn clean package -DskipTests
#
#COPY /target/*.jar /app/application.jar
#
#FROM openjdk:17.0.2-jdk as lancement
#
#WORKDIR /app-lancement
#
#COPY --from=build /app-build/application.jar /app-lancement/application.jar
#
#EXPOSE 8080
#
#ENTRYPOINT ["java", "-jar", "/app/application.jar"]
#
##docker build -t (nom) . (-> localisation)


# Stage 1: Build the application
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app

# Copy configuration and source code
COPY pom.xml .
COPY src ./src

# Build the JAR file
RUN mvn clean package -DskipTests

# Stage 2: Run the application
FROM eclipse-temurin:17-jre-alpine AS lancement

WORKDIR /app

# Copy the generated JAR directly from the 'build' stage
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]