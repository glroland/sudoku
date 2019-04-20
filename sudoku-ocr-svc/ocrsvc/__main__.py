from flask import Flask, render_template, request
from flask_restful import Resource, Api
from werkzeug import secure_filename
from json import dumps
from ocrlib import core

app = Flask(__name__)
api = Api(app)

@app.route('/', methods=['GET'])
def health_test():
    return 'test'

@app.route('/upload', methods=['POST'])
def upload_file():
    f = request.files['file']
    imbytes = f.read()
    im = core.load_image_from_bytes(imbytes)
    digit = core.eval_what_digit(im)
    return 'file uploaded successfully - Length = ' + str(len(imbytes)) + " Digit = " + str(digit) + "\n"

        
if __name__ == '__main__':
     app.run(port='5002')
