FROM adoptopenjdk:11-jre-hotspot as builder
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} productapi.jar
EXPOSE 8080
ENTRYPOINT java -jar productapi.jar