FROM azul/zulu-openjdk:21-jre

WORKDIR /app

COPY ./video/build/libs/video-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 8002

ENTRYPOINT ["java", "-jar", "app.jar"]