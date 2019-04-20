import tensorflow as tf
import numpy as np
from PIL import Image, ImageOps
import io

SUDOKU_MODEL_FILE = 'sudoku_digit.model'
SUDOKU_OCR_DIGIT_WIDTH = 28
SUDOKU_OCR_DIGIT_HEIGHT = 28

def load_image_from_bytes(imbytes):
    im = Image.open(io.BytesIO(imbytes))
    return im

def load_image_from_disk(path):
    im = Image.open(path)
    return im

def preprocess(image):
    newSize = SUDOKU_OCR_DIGIT_WIDTH, SUDOKU_OCR_DIGIT_HEIGHT
    image = image.resize(newSize).convert('L')
    image = ImageOps.invert(image)
    return image

def eval_what_digit(image):
    image = preprocess(image)
    new_model = tf.keras.models.load_model(SUDOKU_MODEL_FILE)
    image_array = np.asarray(image).reshape(1, SUDOKU_OCR_DIGIT_WIDTH * SUDOKU_OCR_DIGIT_HEIGHT)
    predictions = new_model.predict(image_array)
    return np.argmax(predictions[0])


