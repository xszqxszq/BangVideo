FROM python:3.10

WORKDIR /app

COPY debian.sources /etc/apt/sources.list.d/

RUN apt update && \
    apt install ffmpeg -y && \
    apt clean

COPY requirements.txt /tmp/requirements.txt

RUN pip install -i https://mirrors.tuna.tsinghua.edu.cn/pypi/web/simple -r /tmp/requirements.txt

COPY . /app

ENTRYPOINT ["python3", "audit.py"]