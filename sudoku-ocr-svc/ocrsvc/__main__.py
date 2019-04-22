from flask import Flask, render_template, request
from flask_restful import Resource, Api
from werkzeug import secure_filename
import numpy as np
from json import dumps
from ocrlib import core
import json

app = Flask(__name__)
api = Api(app)

class NumpyArrayJSONEncoder(json.JSONEncoder):
    def default(self, obj):
        if isinstance(obj, np.ndarray):
            return obj.tolist()
        return json.JSONEncoder.default(self, obj)

@app.route('/', methods=['GET'])
def health_test():
     return 'test'

@app.route('/upload', methods=['POST'])
def op_upload():
     f = request.files['file']
     imbytes = f.read()
     ocrcore = core.SudokuOCRCore()
     im = ocrcore.load_image_from_bytes(imbytes)
     digit = ocrcore.eval_what_digit(im)
     return 'file uploaded successfully - Length = ' + str(len(imbytes)) + " Digit = " + str(digit) + "\n"

@app.route('/extract', methods=['POST'])
def op_extract():
     f = request.files['file']
     imbytes = f.read()
     ocrcore = core.SudokuOCRCore()
     im = ocrcore.load_image_from_bytes(imbytes)
     puzzle = ocrcore.eval_what_puzzle(im)
     print ('Puzzle extracted successfully - Length = ' + str(len(imbytes)) + " Puzzle = " + str(puzzle) + "\n")
     return json.dumps({'puzzle': {'grid': puzzle} }, cls=NumpyArrayJSONEncoder)
        
if __name__ == '__main__':
     app.run(port='5002')