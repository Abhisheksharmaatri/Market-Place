# ---------- Build stage ----------
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app

# Copy pom.xml and download deps first (cache optimization)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source and build
COPY src ./src
RUN mvn clean package -DskipTests

# ---------- Runtime stage ----------
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copy built jar from builder
COPY --from=builder /app/target/*.jar app.jar

# Render provides PORT dynamically
EXPOSE 8085

# Start Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]
