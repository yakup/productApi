FROM adoptopenjdk:11-jre-hotspot as builder
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} productapi.jar
ENTRYPOINT java -jar productapi.jar