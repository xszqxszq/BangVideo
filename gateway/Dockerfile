FROM debian:bullseye

WORKDIR /app

COPY build/native/nativeCompile/gateway /app/app

EXPOSE 7001

ENTRYPOINT ["/app/app"]