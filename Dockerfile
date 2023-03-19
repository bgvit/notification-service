FROM eclipse-temurin:17 AS TEMP_BUILD_IMAGE
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME
COPY build.gradle.kts settings.gradle.kts gradlew $APP_HOME
COPY gradle $APP_HOME/gradle
RUN ./gradlew build || return 0
COPY . .
RUN ./gradlew build -x test

FROM eclipse-temurin:17-jre-alpine
ENV ARTIFACT_NAME=notification-service-1.0.jar
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME
COPY --from=TEMP_BUILD_IMAGE $APP_HOME/build/libs/$ARTIFACT_NAME .
EXPOSE 8096
CMD ["java","-XX:+UnlockExperimentalVMOptions", "-jar", "notification-service-1.0.jar"]