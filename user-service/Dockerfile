# Build stage
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app

# Copy EVERYTHING needed for Maven reactor
COPY pom.xml .
COPY product-service ./product-service
COPY discovery-service ./discovery-service
COPY inventory-service ./inventory-service
COPY order-service ./order-service
COPY api-gateway ./api-gateway
COPY user-service ./user-service

RUN mvn -pl user-service -am clean package -DskipTests

# ---------- Runtime stage ----------
FROM eclipse-temurin:17-jre
WORKDIR /app

COPY --from=builder /app/user-service/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
