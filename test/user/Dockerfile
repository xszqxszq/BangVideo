FROM azul/zulu-openjdk:21-jre

WORKDIR /app

COPY ./user/build/libs/user-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 8001

ENTRYPOINT ["java", "-jar", "app.jar"]