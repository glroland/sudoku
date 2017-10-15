package com.glroland.sudoku.util;

import java.util.ArrayList;

public class SudokuConstants {

	public static final int GRID_WIDTH = 3;
	
	public static final int PUZZLE_GRID_SIZE = GRID_WIDTH * GRID_WIDTH;
	public static final int PUZZLE_WIDTH = PUZZLE_GRID_SIZE;
	public static final int PUZZLE_HEIGHT = PUZZLE_GRID_SIZE;
	public static final int PUZZLE_BLOCK_COUNT = PUZZLE_WIDTH * PUZZLE_HEIGHT;
	
	public static final int VALUE_EMPTY = 0;
	public static final int VALUE_MIN = 1;
	public static final int VALUE_MAX = PUZZLE_GRID_SIZE;
	
	public static final ArrayList<Integer> ALL_VALUES;
	
	static {
		// create basic value array that can be cloned
		ALL_VALUES = new ArrayList<Integer>();
		for (int i=1; i<= SudokuConstants.PUZZLE_WIDTH; i++)
			ALL_VALUES.add(i);
	}

}
