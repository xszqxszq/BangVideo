FROM debian:bullseye

WORKDIR /app

COPY build/native/nativeCompile/file /app/app

COPY build/native/nativeCompile/*.so /app/

EXPOSE 8004

ENTRYPOINT ["/app/app"]