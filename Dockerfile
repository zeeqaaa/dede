FROM openjdk:17-jdk-slim
WORKDIR /app
ARG JAR_FILE=target/ChecksumWebApp-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
COPY /data /data
ENTRYPOINT ["java", "-jar", "app.jar"]