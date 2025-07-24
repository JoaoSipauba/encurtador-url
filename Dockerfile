FROM ubuntu:latest AS build

RUN apt-get update && apt-get install openjdk-17-jdk -y
COPY . .

RUN apt-get install maven -y
RUN mvn clean install -DskipTests

FROM openjdk:17-jdk-slim AS runtime

EXPOSE 8080

COPY --from=build /target/*.jar app.jar

CMD ["java", "-jar", "app.jar"]