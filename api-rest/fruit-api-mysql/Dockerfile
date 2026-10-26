# CONSTRUCTION STEP

FROM maven:3.10.0-amazoncorretto-21-alpine AS build-fruit-app

WORKDIR /fruit-app

COPY pom.xml ./

COPY src/ ./src/

RUN mvn clean package -DskipTests

# PRODUCTION STEP

FROM amazoncorretto:21-alpine

WORKDIR /fruit-app

COPY --from=build-fruit-app /fruit-app/target/fruit-api-MYSQL-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 9000

CMD ["java", "-jar", "app.jar"]