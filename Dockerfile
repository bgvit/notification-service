FROM eclipse-temurin:17 AS TEMP_BUILD_IMAGE
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME
COPY build.gradle.kts settings.gradle.kts gradlew $APP_HOME
COPY gradle $APP_HOME/gradle
RUN ./gradlew build || return 0
COPY . .
RUN ./gradlew build -x test

# This image was chosen because:
# - JRE only to run the server, not necessary JDK to run it;
# - tiny size;
# - lack of known security vulnerabilities.
FROM eclipse-temurin:17-jre-alpine
# Run as non-root for security purposes
RUN addgroup simplegroup; adduser  --ingroup simplegroup --disabled-password simpleuser
USER simpleuser
# -
ENV ARTIFACT_NAME=notification-service-1.0.jar
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME
COPY --from=TEMP_BUILD_IMAGE $APP_HOME/build/libs/$ARTIFACT_NAME .
EXPOSE 8080
CMD ["java", "-jar", "notification-service-1.0.jar"]