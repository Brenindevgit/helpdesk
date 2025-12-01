
FROM maven:3.8.5-openjdk-11 AS build
COPY . .
RUN mvn clean package -DskipTests
FROM eclipse-temurin:11-jdk-alpine
VOLUME /tmp
COPY --from=build /target/helpdesk-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]