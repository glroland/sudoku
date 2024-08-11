"""${{values.artifact_id}}

${{values.description}}
"""
import requests

import uvicorn
from fastapi import FastAPI
from json import dumps
import json
import numpy as np
from ocrlib import core

app = FastAPI()


class NumpyArrayJSONEncoder(json.JSONEncoder):
     def default(self, obj):
#          self.log = logging.getLogger(self.__class__.__name__)
          if isinstance(obj, np.ndarray):
               return obj.tolist()
          return json.JSONEncoder.default(self, obj)


@app.get("/")
def default_response():
    """Provide a simple textual response to the root url to verify the application is working.
    """
    return {"message": "Hello, thanks for invoking the Sudoku OCR Microservice!!!  Enjoy your game."}


@app.post("/upload")
def op_upload(self, obj):
     f = request.files['file']
     imbytes = f.read()
     print("Upload called with file of length: " + str(len(imbytes)))
     ocrcore = core.SudokuOCRCore()
     im = ocrcore.load_image_from_bytes(imbytes)
     digit = ocrcore.eval_what_digit(im)
     return 'file uploaded successfully - Length = ' + str(len(imbytes)) + " Digit = " + str(digit) + "\n"


@app.post("/extract")
def op_extract():
     f = request.files['file']
     imbytes = f.read()          
     print("Upload called with file of length: " + str(len(imbytes)))

     ocrcore = core.SudokuOCRCore()
     im = ocrcore.load_image_from_bytes(imbytes)
     puzzle = ocrcore.eval_what_puzzle(im)
     print ('Puzzle extracted successfully - Length = ' + str(len(imbytes)) + " Puzzle = " + str(puzzle) + "\n")
     return json.dumps({'puzzle': {'grid': puzzle} }, cls=NumpyArrayJSONEncoder)


if __name__ == "__main__":
    uvicorn.run(app, host="0.0.0.0", port=8080)
