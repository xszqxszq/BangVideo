FROM debian:bullseye

WORKDIR /app

COPY build/native/nativeCompile/community /app/app

COPY build/native/nativeCompile/*.so /app/

EXPOSE 8003

ENTRYPOINT ["/app/app"]