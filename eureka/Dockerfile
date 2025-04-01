FROM alibabadragonwell/dragonwell:21

WORKDIR /app

COPY build/libs/eureka-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 7000

ENTRYPOINT ["java", "-jar", "app.jar"]