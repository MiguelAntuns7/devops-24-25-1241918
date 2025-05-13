# Base image with a lightweight Java 11 Runtime, ideal for deployment scenarios
FROM openjdk:11-jre-slim

# Install only required packages (wget) and clean up to minimize image size
RUN apt-get update \
    && apt-get install -y wget \
    && rm -rf /var/lib/apt/lists/*

# Define the working directory inside the container
WORKDIR /usr/src/app

# Fetch the H2 database JAR from the central Maven repository
RUN wget https://repo1.maven.org/maven2/com/h2database/h2/1.4.200/h2-1.4.200.jar

# Open ports for the H2 web console (8082) and TCP client connections (9092)
EXPOSE 9092 8082

# Start the H2 server with web and TCP modes active, allowing connections and auto-creating databases as needed
CMD ["java", "-cp", "./h2-1.4.200.jar", "org.h2.tools.Server", \
     "-tcp", "-tcpAllowOthers", "-ifNotExists", \
     "-web", "-webAllowOthers"]