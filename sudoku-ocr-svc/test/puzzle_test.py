import unittest
import numpy as np
from ocrlib import core
import array

class TestPuzzleOCR(unittest.TestCase):

    def test_puzzlepng(self):
        ocrcore = core.SudokuOCRCore()
        im = ocrcore.load_image_from_disk("test/images/puzzle.png")
        expected = array.array('i',[ 
            5, 3, 0, 0, 7, 0, 0, 0, 0, 
            6, 0, 0, 1, 9, 5, 0, 0, 0, 
            0, 9, 8, 0, 0, 0, 0, 6, 0, 
            8, 0, 0, 0, 6, 0, 0, 0, 3, 
            4, 0, 0, 8, 0, 3, 0, 0, 1, 
            7, 0, 0, 0, 2, 0, 0, 0, 6, 
            0, 6, 0, 0, 0, 0, 2, 8, 0, 
            0, 0, 0, 4, 1, 9, 0, 0, 5, 
            0, 0, 0, 0, 8, 0, 0, 7, 9 
            ])
        actual = ocrcore.eval_what_puzzle(im, expected)
        self.assertEquals(len(actual), len(expected))
        np.testing.assert_array_equal(expected, actual, 'puzzle.png', True)
        if (np.array_equal(expected, actual) == True):
            print ("THEY MATCH!!!!  I Can't Believe it!")



if __name__ == '__main__':
    unittest.main()
