FROM alibabadragonwell/dragonwell:21-ubuntu

RUN apt update && \
    apt install -y ffmpeg && \
    apt clean

WORKDIR /app

COPY build/libs/encoding-0.0.1-SNAPSHOT.jar /app/app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]