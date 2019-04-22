import tensorflow as tf
import numpy as np
from PIL import Image, ImageOps
import io
import array
import os
from ocrlib import constants

class SudokuOCRCore(object):

    SUDOKU_EMPTY_GRID = None
    SODOKU_MODEL = None

    def __init__(self):
        self.SUDOKU_EMPTY_GRID = array.array('i',(0 for i in range(0, constants.SudokuOCRConstants.SUDOKU_GRID_WIDTH * constants.SudokuOCRConstants.SUDOKU_GRID_HEIGHT)))
        self.SODOKU_MODEL = tf.keras.models.load_model(constants.SudokuOCRConstants.SUDOKU_MODEL_FILE)

    def load_image_from_bytes(self, imbytes):
        im = Image.open(io.BytesIO(imbytes))
        return im

    def load_image_from_disk(self, path):
        im = Image.open(path)
        return im

    def preprocess(self, image):
        image = image.convert('L')
        if self.should_invert(image):
            image = ImageOps.invert(image)
        #    image = trim(image)
        newSize = constants.SudokuOCRConstants.SUDOKU_OCR_DIGIT_WIDTH, constants.SudokuOCRConstants.SUDOKU_OCR_DIGIT_HEIGHT
        image = image.resize(newSize)
        return image

    def trim(self, image):
        topX = 0
        topY = 0
        bottomX = image.width - 1
        bottomY = image.height - 1

        trimFlag = True
        while (trimFlag == True) and ((bottomX - topX + 1) >= constants.SudokuOCRConstants.SUDOKU_OCR_DIGIT_WIDTH) and ((bottomY - topY + 1) >= constants.SudokuOCRConstants.SUDOKU_OCR_DIGIT_HEIGHT):

            if self.trim_check_row(image, topY) == True:
                topY = topY + 1
                print("trimming topY")
                trimFlag = True

            elif self.trim_check_row(image, bottomY) == True:
                bottomY = bottomY - 1
                print("trimming bottomY")
                trimFlag = True

            elif self.trim_check_column(image, topX) == True:
                topX = topX + 1
                print("trimming topX")
                trimFlag = True

            elif self.trim_check_column(image, bottomX) == True:
                bottomX = bottomX - 1
                print("trimming bottomX")
                trimFlag = True

            else:
                trimFlag = False

        shrinkX = image.width - (bottomX - topX + 1)
        if (shrinkX > (2 * constants.SudokuOCRConstants.SUDOKU_MIN_PADDING)):
            topX = topX - constants.SudokuOCRConstants.SUDOKU_MIN_PADDING
            bottomX = bottomX + constants.SudokuOCRConstants.SUDOKU_MIN_PADDING

        shrinkY = image.height - (bottomY - topY + 1)
        if (shrinkY > (2 * constants.SudokuOCRConstants.SUDOKU_MIN_PADDING)):
            topY = topY - constants.SudokuOCRConstants.SUDOKU_MIN_PADDING
            bottomY = bottomY + constants.SudokuOCRConstants.SUDOKU_MIN_PADDING

        print("TRIMMING: width=" + str(image.width) + " height=" + str(image.height) + " topX=" + str(topX) + " topY=" + str(topY) + " bottomX=" + str(bottomX) + " bottomY=" + str(bottomY))
        image = image.crop([topX, topY, bottomX, bottomY])

        return image

    def trim_check_row(self, image, y):
        for x in range(image.width):
            if int(image.getpixel((x, y)) > constants.SudokuOCRConstants.SUDOKU_BLANK_THRESHOLD):
                return False
        return True

    def trim_check_column(self, image, x):
        for y in range(image.height):
            if int(image.getpixel((x, y)) > constants.SudokuOCRConstants.SUDOKU_BLANK_THRESHOLD):
                return False
        return True

    def should_invert(self, image):
        total_color = 0
        for y in range(image.height):
            for x in range(image.width):
                total_color = total_color + image.getpixel((x, y))
        avg_color = total_color / (image.width * image.height)
        if avg_color >= 128:
            return True
        else:
            return False

    def eval_what_digit(self, image, expectedDigit = None):
        image = self.preprocess(image)
        image_array = np.asarray(image).reshape(1, constants.SudokuOCRConstants.SUDOKU_OCR_DIGIT_WIDTH * constants.SudokuOCRConstants.SUDOKU_OCR_DIGIT_HEIGHT)
        predictions = self.SODOKU_MODEL.predict(image_array)
        if expectedDigit != None:
            print(predictions)
        return np.argmax(predictions[0])

    def eval_what_puzzle(self, image, expected = None):
        result = np.copy(self.SUDOKU_EMPTY_GRID)

        squareWidth = int(image.width / constants.SudokuOCRConstants.SUDOKU_GRID_WIDTH)
        squareHeight = int(image.height / constants.SudokuOCRConstants.SUDOKU_GRID_HEIGHT)
        padWidth = (constants.SudokuOCRConstants.SUDOKU_OCR_DIGIT_PAD_PERCENT * squareWidth) / 100
        padHeight = (constants.SudokuOCRConstants.SUDOKU_OCR_DIGIT_PAD_PERCENT * squareHeight) / 100
        print ("SquareWidth=" + str(squareWidth) + " SquareHeight=" + str(squareHeight) + " padWidth=" + str(padWidth) + " padHeight=" + str(padHeight))

        for y in range(0, constants.SudokuOCRConstants.SUDOKU_GRID_HEIGHT):
            squareTopY = (squareHeight * y)
            squareBottomY = (squareHeight * (y + 1)) - 1
            for x in range(0, constants.SudokuOCRConstants.SUDOKU_GRID_WIDTH):
                squareTopX = (squareWidth * x)
                squareBottomX = (squareWidth * (x + 1)) - 1
                digitImage = image.crop([squareTopX + padWidth, squareTopY + padHeight, squareBottomX - padWidth, squareBottomY - padHeight])
                expectedDigit = None
                if expected != None:
                    index = (y * constants.SudokuOCRConstants.SUDOKU_GRID_WIDTH) + x
                    expectedDigit = expected[index]
                digit = self.eval_what_digit(digitImage, expectedDigit)
                result[x + (y * constants.SudokuOCRConstants.SUDOKU_GRID_WIDTH)] = digit

                print("squareTopX=" + str(squareTopX) + " squareTopY=" + str(squareTopY) + " squareBottomX=" + str(squareBottomX) + " squareBottomY=" + str(squareBottomY), " digit=" + str(digit))
                if expected != None:
                    index = (y * constants.SudokuOCRConstants.SUDOKU_GRID_WIDTH) + x
                if expected[index] != digit:
                    print("XXX Expected=" + str(expected[index]) + " and Received=" + str(digit))
                    digitImage.show(digitImage)
    
        return result


