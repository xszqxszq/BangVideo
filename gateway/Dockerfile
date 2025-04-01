FROM alibabadragonwell/dragonwell:21

WORKDIR /app

COPY build/libs/gateway-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 7001

ENTRYPOINT ["java", "-jar", "app.jar"]