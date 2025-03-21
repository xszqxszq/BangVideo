FROM alibabadragonwell/dragonwell:21

WORKDIR /app

COPY build/libs/community-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 8003

ENTRYPOINT ["java", "-jar", "app.jar"]