FROM debian:bullseye

WORKDIR /app

COPY build/native/nativeCompile/user /app/app

COPY build/native/nativeCompile/*.so /app/

EXPOSE 8001

ENTRYPOINT ["/app/app"]