FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /workspace

COPY gradlew settings.gradle build.gradle ./
COPY gradle gradle
RUN ./gradlew dependencies --no-daemon

COPY src src
RUN ./gradlew bootJar --no-daemon

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

RUN addgroup -S cashcards && adduser -S cashcards -G cashcards
COPY --from=build /workspace/build/libs/*.jar app.jar
USER cashcards

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
