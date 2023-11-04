FROM openjdk:17-jdk-alpine as build

WORKDIR /home/app

COPY gradlew /home/app/
COPY gradle /home/app/gradle
COPY build.gradle /home/app/
COPY settings.gradle /home/app/
COPY src /home/app/src

RUN chmod +x /home/app/gradlew

RUN ./gradlew build --no-daemon

FROM openjdk:17-jdk-alpine

COPY --from=build /home/app/build/libs/*.jar /app/makemate-0.0.1.jar

ENTRYPOINT ["java","-jar","/app/makemate-0.0.1.jar"]
