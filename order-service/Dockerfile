FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app

# copy parent pom
COPY pom.xml .

# copy all modules from ROOT
COPY user-service user-service
COPY product-service product-service
COPY discovery-service discovery-service
COPY inventory-service inventory-service
COPY order-service order-service
COPY api-gateway api-gateway


RUN ls -la /app
RUN mvn -X -pl order-service -am clean package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=builder /app/order-service/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
