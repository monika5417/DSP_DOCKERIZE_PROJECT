# Jo image aapne already pull kar li hai - USE THAT!
FROM maven:3.6.3-jdk-8-slim AS build

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn clean package -DskipTests

# Ye bhi aapne pull kar li hai
FROM azul/zulu-openjdk-alpine:8-jre

WORKDIR /app
COPY --from=build /app/target/deposit_scheme.war app.war

EXPOSE 8083
ENTRYPOINT ["java", "-jar", "app.war"]