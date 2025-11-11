# Use official Maven image to build the app
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code and build
COPY src ./src
RUN mvn clean package -DskipTests

# Run the app using a lightweight JDK image
FROM eclipse-temurin:17-jdk
WORKDIR /app

# Copy built jar from previous stage
COPY --from=build /app/target/*.jar app.jar

# Expose port (Render uses PORT variable)
EXPOSE 8080
ENV PORT=8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
