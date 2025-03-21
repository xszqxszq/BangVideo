FROM alibabadragonwell/dragonwell:21

WORKDIR /app

COPY build/libs/video-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 8002

ENTRYPOINT ["java", "-jar", "app.jar"]