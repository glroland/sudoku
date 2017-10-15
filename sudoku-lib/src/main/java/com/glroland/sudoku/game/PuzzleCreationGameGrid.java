package com.glroland.sudoku.game;

import java.util.ArrayList;

import com.glroland.sudoku.util.SudokuConstants;

public class PuzzleCreationGameGrid implements Cloneable
{
	private ArrayList<Integer> [] grid = new ArrayList[SudokuConstants.PUZZLE_SIZE];
	
	public ArrayList<Integer> getMoveList(int i)
	{
		return grid[i];
	}
	
	public ArrayList<Integer> getMoveList(int x, int y)
	{
		return grid[x + (y*SudokuConstants.PUZZLE_WIDTH)];
	}

	public Object clone()
	{
		PuzzleCreationGameGrid freshCopy = new PuzzleCreationGameGrid();
		
		ArrayList<Integer> [] copy = freshCopy.grid;
		for (int i=0; i < copy.length; i++)
		{
			if (grid[i] != null)
			{
				copy[i] = new ArrayList<Integer>();
				copy[i].addAll(grid[i]);
			}
		}
		
		freshCopy.grid = copy;
		return freshCopy;
	}
}
