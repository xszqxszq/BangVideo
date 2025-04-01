FROM debian:bullseye

WORKDIR /app

COPY build/native/nativeCompile/video /app/app

COPY build/native/nativeCompile/*.so /app/

EXPOSE 8002

ENTRYPOINT ["/app/app"]