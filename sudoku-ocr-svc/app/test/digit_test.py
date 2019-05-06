import unittest
from ocrlib import core

class TestDigitOCR(unittest.TestCase):

    def test_one(self):
        ocrcore = core.SudokuOCRCore()
        im = ocrcore.load_image_from_disk("test/images/one.png")
        self.assertEquals(1, ocrcore.eval_what_digit(im))

    def test_five(self):
        ocrcore = core.SudokuOCRCore()
        im = ocrcore.load_image_from_disk("test/images/five.png")
        self.assertEquals(5, ocrcore.eval_what_digit(im))


if __name__ == '__main__':
    unittest.main()

