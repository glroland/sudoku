import unittest
from ocrlib import core

class TestDigitOCR(unittest.TestCase):

    def test_one(self):
        im = core.load_image("test/images/one.png")
        self.assertEquals(1, core.eval_what_digit(im))

    def test_five(self):
        im = core.load_image("test/images/five.png")
        self.assertEquals(5, core.eval_what_digit(im))


if __name__ == '__main__':
    unittest.main()

