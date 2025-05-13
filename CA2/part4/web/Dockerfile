# Use a minimal JDK 17 base image, well-suited for Java app builds
FROM openjdk:17-jdk-slim

# Refresh package index, install Git for cloning, then clean up to reduce image size
RUN apt-get update \
    && apt-get install -y git \
    && rm -rf /var/lib/apt/lists/*

# Define the container's working directory
WORKDIR /usr/src/app

# Clone the application repository into the container
RUN git clone https://github.com/MiguelAntuns7/devops-24-25-1241918.git .

# Navigate to the specific Spring Boot project folder
WORKDIR /usr/src/app/CA1/part3/react-and-spring-data-rest-basic

# Ensure Gradle wrapper is executable and build the project JAR
RUN chmod +x gradlew \
    && ./gradlew clean bootJar

# Make port 8080 available for external access
EXPOSE 8080

# Launch the Spring Boot application using the generated JAR
CMD ["java", "-jar", "build/libs/react-and-spring-data-rest-basic-0.0.1-SNAPSHOT.jar"]