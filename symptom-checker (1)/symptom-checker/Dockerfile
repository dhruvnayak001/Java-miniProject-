# Use OpenJDK 21 runtime
FROM eclipse-temurin:21-jdk

# Set working directory inside container
WORKDIR /app

# Copy the JAR file (update name if different)
COPY target/symptom-checker-0.0.1-SNAPSHOT.jar app.jar

# Expose Spring Boot default port
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
