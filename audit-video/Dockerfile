FROM python:3.10

WORKDIR /app

COPY . /app

RUN pip install -r requirements.txt

RUN apt update && \
    apt install ffmpeg -y && \
    apt clean

ENTRYPOINT ["python3", "audit.py"]