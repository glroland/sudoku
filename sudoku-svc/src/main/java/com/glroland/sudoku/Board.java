package com.glroland.sudoku;

import com.glroland.sudoku.util.SudokuConstants;

public class Board 
{
	private int [] [] grid;

	public Board()
	{
		grid = new int[SudokuConstants.PUZZLE_WIDTH][SudokuConstants.PUZZLE_HEIGHT];
	}
	
	public Board (int [][] g)
	{
		grid = g;
	}
	
	public int[][] getGrid() {
		return grid;
	}

	public void setGrid(int[][] grid) {
		this.grid = grid;
	}
}
