package com.glroland.sudoku.game;

import java.util.ArrayList;

import com.glroland.sudoku.model.GameGrid;
import com.glroland.sudoku.util.SudokuConstants;

public class PuzzleCreationGameGrid implements Cloneable
{
	@SuppressWarnings("unchecked")
	private ArrayList<Integer> [] grid = new ArrayList[SudokuConstants.PUZZLE_BLOCK_COUNT];
	
	public PuzzleCreationGameGrid()
	{
		for (int i = 0; i < grid.length; i++)
			grid[i] = new ArrayList<Integer>();
	}
	
	public ArrayList<Integer> getMoveList(int i)
	{
		return grid[i];
	}
	
	public ArrayList<Integer> getMoveList(int x, int y)
	{
		return grid[x + (y*SudokuConstants.PUZZLE_WIDTH)];
	}

	public void cleanseValue(int value, int pivot)
	{
		// remove it from every cell in the row
		int endI = ((pivot / SudokuConstants.PUZZLE_WIDTH) * SudokuConstants.PUZZLE_WIDTH) + SudokuConstants.PUZZLE_WIDTH - 1;
		for (int clear = pivot + 1; clear <= endI; clear++)
		{
			ArrayList<Integer> vi = grid[clear];
			vi.remove(new Integer(value));
		}
		
		// remove it from every cell in the column
		for (int clear = pivot + SudokuConstants.PUZZLE_WIDTH; clear < grid.length; clear += SudokuConstants.PUZZLE_WIDTH)
		{
			ArrayList<Integer> vi = grid[clear];
			vi.remove(new Integer(value));
		}
		
		// remove it from every cell in the quadrant
		int x = pivot % SudokuConstants.PUZZLE_WIDTH;
		int y = pivot / SudokuConstants.PUZZLE_WIDTH;
		int gridStartX = (x / SudokuConstants.GRID_WIDTH) * SudokuConstants.GRID_WIDTH;
		int gridStartY = (y / SudokuConstants.GRID_WIDTH) * SudokuConstants.GRID_WIDTH;
		int gridEndX = gridStartX + SudokuConstants.GRID_WIDTH - 1;
		int gridEndY = gridStartY + SudokuConstants.GRID_WIDTH - 1;
		for (int gy = gridStartY; gy <= gridEndY; gy++)
			for (int gx = gridStartX; gx <= gridEndX; gx++)
			{
				if ((gy != y) && (gx != x))
				{
					ArrayList<Integer> vi = grid[gx + (gy * SudokuConstants.PUZZLE_WIDTH)];
					vi.remove(new Integer(value));
				}
			}
	}

	public GameGrid getResultingGrid()
	{
		int [] values = new int [SudokuConstants.PUZZLE_BLOCK_COUNT];
		for (int i=0; i<SudokuConstants.PUZZLE_BLOCK_COUNT; i++)
		{
			ArrayList<Integer> pos = grid[i];
			if (pos.size() != 1)
				throw new RuntimeException("Unable to translate PuzzleCreationGameGrid to GameGrid because puzzle isn't completed!  Pos=" + i);
			values[i] = pos.get(0);
		}
		
		GameGrid grid = new GameGrid(values);
		return grid;
	}

	public boolean isComplete()
	{
		GameGrid gameGrid = this.getResultingGrid();
		return gameGrid.isValidBoard() && gameGrid.isSolved();
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
