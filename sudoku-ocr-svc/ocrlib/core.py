import tensorflow as tf
import numpy as np
from PIL import Image, ImageOps

SUDOKU_MODEL_FILE = 'sudoku_digit.model'
SUDOKU_OCR_DIGIT_WIDTH = 28
SUDOKU_OCR_DIGIT_HEIGHT = 28

def load_image(path):
    im = Image.open(path)
    newSize = SUDOKU_OCR_DIGIT_WIDTH, SUDOKU_OCR_DIGIT_HEIGHT
    im = im.resize(newSize).convert('L')
    im = ImageOps.invert(im)
    return im


def eval_what_digit(image):
    new_model = tf.keras.models.load_model(SUDOKU_MODEL_FILE)
    image_array = np.asarray(image).reshape(1, SUDOKU_OCR_DIGIT_WIDTH * SUDOKU_OCR_DIGIT_HEIGHT)
    predictions = new_model.predict(image_array)
    return np.argmax(predictions[0])


