from flask import Flask, render_template, request
from flask_cors import CORS
from json import dumps
import json
import numpy as np
from ocrlib import core

# from flask_restful import Resource, Api
# from werkzeug.utils import secure_filename
# import logging

app = Flask(__name__)
# api = Api(app)
CORS(app)


class NumpyArrayJSONEncoder(json.JSONEncoder):
     def default(self, obj):
#          self.log = logging.getLogger(self.__class__.__name__)
          if isinstance(obj, np.ndarray):
               return obj.tolist()
          return json.JSONEncoder.default(self, obj)


@app.route('/', methods=['GET', 'POST'])
def getRoot():
     print("HTTP GET request received for root path")
     return 'Hello, thanks for invoking the Sudoku OCR Microservice!!!  Enjoy your game.'

@app.route('/hello/', methods=['GET', 'POST'])
def getHello():
     print("HTTP GET request received for root path")
     return 'Hello from sudoku-ocr-svc!'

@app.route('/upload', methods=['POST'])
def op_upload(self, obj):
     f = request.files['file']
     imbytes = f.read()
     print("Upload called with file of length: " + str(len(imbytes)))
     ocrcore = core.SudokuOCRCore()
     im = ocrcore.load_image_from_bytes(imbytes)
     digit = ocrcore.eval_what_digit(im)
     return 'file uploaded successfully - Length = ' + str(len(imbytes)) + " Digit = " + str(digit) + "\n"

@app.route('/extract', methods=['POST'])
def op_extract():
     f = request.files['file']
     imbytes = f.read()          
     print("Upload called with file of length: " + str(len(imbytes)))

     ocrcore = core.SudokuOCRCore()
     im = ocrcore.load_image_from_bytes(imbytes)
     puzzle = ocrcore.eval_what_puzzle(im)
     print ('Puzzle extracted successfully - Length = ' + str(len(imbytes)) + " Puzzle = " + str(puzzle) + "\n")
     return json.dumps({'puzzle': {'grid': puzzle} }, cls=NumpyArrayJSONEncoder)


if __name__ == '__main__':
     app.run()
