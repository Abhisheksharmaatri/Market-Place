# ---------- Build stage ----------
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app

# Copy parent POM
COPY pom.xml .

# Copy ALL modules declared in parent pom.xml
COPY user-service ./user-service
COPY product-service ./product-service
COPY discovery-service ./discovery-service
COPY inventory-service ./inventory-service
COPY order-service ./order-service
COPY api-gateway ./api-gateway

# Build only user-service (but validate full reactor)
RUN mvn -pl user-service -am clean package -DskipTests

# ---------- Runtime stage ----------
FROM eclipse-temurin:17-jre
WORKDIR /app

COPY --from=builder /app/user-service/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
