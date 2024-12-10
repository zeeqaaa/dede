# Spring Boot Docker Application

## Description
This project contains a Spring Boot application that calculates checksums, persists results in a database, and runs in a Dockerized environment.

## Steps to Run

1. Build the Spring Boot project JAR file:
    ```bash
    mvn clean package
    ```

2. Build and start the Docker containers:
    ```bash
    docker-compose up 
    ```

3. Access the application at `http://localhost:8080`.

## Endpoints
- `POST /api/checksum`: Calculate and store the checksum.
- `GET /api/entries`: List all stored entries.

