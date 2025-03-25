FROM python:3.10

WORKDIR /app
#
COPY requirements.txt /tmp/

RUN pip install -r /tmp/requirements.txt

RUN apt update && \
    apt install ffmpeg -y && \
    apt clean

ENTRYPOINT ["python3", "audit.py"]