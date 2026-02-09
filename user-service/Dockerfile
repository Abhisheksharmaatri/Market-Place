FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app

# Copy root pom (PARENT)
COPY pom.xml .

# Copy all modules
COPY user-service ./user-service

# Build only user-service module
RUN mvn -pl user-service -am clean package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=builder /app/user-service/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
