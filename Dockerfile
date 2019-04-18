FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY infra/rest/build/libs/infra-rest-1.0-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]