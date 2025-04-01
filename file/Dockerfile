FROM debian:bullseye

WORKDIR /app

COPY build/native/nativeCompile/file /app/app

EXPOSE 8004

ENTRYPOINT ["/app/app"]