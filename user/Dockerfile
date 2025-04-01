FROM debian:bullseye

WORKDIR /app

COPY build/native/nativeCompile/user /app/app

EXPOSE 8001

ENTRYPOINT ["/app/app"]