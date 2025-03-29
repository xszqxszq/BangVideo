import json
import os
import pika
import shutil
import subprocess
import uuid
from PIL import Image
from scenedetect import detect, ContentDetector
from transformers import pipeline

VIDEO_DIR = '/static/video'
TMP_DIR = os.path.join(VIDEO_DIR, 'temp')
THRESHOLD = 10

detector = ContentDetector(THRESHOLD)
classifier = pipeline("image-classification", model="./nsfw_image_detection")

rabbit = pika.BlockingConnection(pika.ConnectionParameters(
    host = os.environ.get('RABBITMQ_HOST', 'localhost'),
    port = int(os.environ.get('RABBITMQ_PORT', '5672')),
    virtual_host = '/',
    credentials = pika.PlainCredentials(
        'guest',
        'guest'
    )
)).channel()

rabbit.queue_declare(queue = 'video.audit.queue')

def audit(path):
    scenes = [(begin.get_frames() + end.get_frames()) // 2 for (begin, end) in detect(path, detector)]
    select_filter = "+".join([f"eq(n\\,{f})" for f in scenes])

    output = os.path.join(TMP_DIR, uuid.uuid4().hex)
    os.mkdir(output)

    cmd = [
        "ffmpeg",
        "-i", path,
        "-vf", f"select='{select_filter}'",
        "-vsync", "0",
        f"{output}/%d.jpg",
        "-y"
    ]
    subprocess.run(cmd, check=True, stdout=subprocess.DEVNULL, stderr=subprocess.DEVNULL)
    nsfw = []
    for index, scene in enumerate(scenes):
        image = Image.open(f'{output}/{index+1}.jpg')
        label = classifier(image)[0]
        if label['label'] == 'nsfw':
            nsfw.append((label['score'], scene))

    shutil.rmtree(output)

    if len(nsfw) == 0:
        return {'pass': True, 'frames': []}
    average = sum(i[0] for i in nsfw) / len(nsfw)

    if any(i[0] > 0.95 for i in nsfw) or average > 0.8:
        return {'pass': False, 'frames': [i[1] for i in nsfw]}
    return {'pass': True, 'frames': []}

def callback(ch, method, properties, body):
    request = json.loads(body.decode('UTF-8'))
    vid = request['id']
    cid = request['cid']
    ch.basic_ack(delivery_tag = method.delivery_tag)
    result = audit(os.path.join(VIDEO_DIR, str(cid), '360.mp4'))
    result['id'] = vid
    result['cid'] = cid
    ch.basic_publish(
        exchange = '',
        routing_key = 'video.audit.finished',
        body = json.dumps(result)
    )

if __name__ == "__main__":
    rabbit.basic_qos(prefetch_count=1)
    rabbit.basic_consume(
        queue='video.audit.queue',
        auto_ack=False,
        on_message_callback=callback
    )
    rabbit.start_consuming()
