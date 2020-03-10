FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
#COPY ./config /config
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.config.location=/config/","-jar","/app.jar"]