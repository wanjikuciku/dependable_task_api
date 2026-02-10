# Use official lightweight Java 17 image
FROM eclipse-temurin:17-jre

# Set working directory inside container
WORKDIR /app

# Copy the built jar into the container
COPY target/dependable-task-api-0.0.1-SNAPSHOT.jar app.jar

# Expose application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
