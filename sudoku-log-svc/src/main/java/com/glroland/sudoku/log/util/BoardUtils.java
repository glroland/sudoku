package com.glroland.sudoku.log.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BoardUtils {
    
    private static final Logger log = LoggerFactory.getLogger(BoardUtils.class);

    public static final int [] convertToIntArray(String board)
    {
        // first validate string length to ensure that its a proper board
        if (board == null) 
        {
            String msg = "Input parameter board is null or empty";
            log.error(msg);
            throw new RuntimeException(msg);
        }
        if (board.length() != Constants.BOARD_SQUARES) 
        {
            String msg = "Input parameter board has an improper size.  Expected=" + Constants.BOARD_SQUARES + ", Received=" + board.length();
            log.error(msg);
            throw new RuntimeException(msg);
        }

        // mirror board to an int array
        int [] intArray = new int[Constants.BOARD_SQUARES];
        for (int i=0; i < Constants.BOARD_SQUARES; i++)
        {
            String square = board.substring(i, i+1);
            if (" ".equals(square))
                intArray[i] = 0;
            else
                intArray[i] = Integer.parseInt(square);
        }

        return intArray;
    }
}
