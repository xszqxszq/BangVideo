FROM alibabadragonwell/dragonwell:21

WORKDIR /app

COPY build/libs/file-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 8004

ENTRYPOINT ["java", "-jar", "app.jar"]