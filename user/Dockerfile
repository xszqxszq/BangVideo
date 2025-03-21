FROM alibabadragonwell/dragonwell:21

WORKDIR /app

COPY build/libs/user-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 8001

ENTRYPOINT ["java", "-jar", "app.jar"]