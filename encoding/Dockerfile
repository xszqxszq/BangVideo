FROM debian:bullseye

COPY sources.list /etc/apt/

RUN apt update && \
    apt install -y ffmpeg && \
    apt clean

WORKDIR /app

COPY build/native/nativeCompile/encoding /app/app

ENTRYPOINT ["/app/app"]