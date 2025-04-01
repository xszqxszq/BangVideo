FROM debian:bullseye

WORKDIR /app

COPY build/native/nativeCompile/community /app/app

EXPOSE 8003

ENTRYPOINT ["/app/app"]