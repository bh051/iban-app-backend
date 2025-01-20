FROM eclipse-temurin:21-jdk

ARG JAR_FILE
COPY $JAR_FILE iban-example-app.jar

VOLUME /opt/config

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "iban-example-app.jar", "--spring.config.location=/opt/config/config.yml"]