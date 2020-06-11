FROM openjdk:11.0.7
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring$ ./gradlew bootBuildImage --imageName=springio/gs-spring-boot-docker
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["sh", "-c", "java -jar /app.jar"]