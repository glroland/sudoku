package com.glroland.sudoku.game;

import com.glroland.sudoku.model.GameGrid;
import com.glroland.sudoku.model.PlayableGameGrid;
import com.glroland.sudoku.util.SudokuConstants;

public class PuzzleFactory 
{
	public static Puzzle createPuzzle()
	{
		PlayableGameGrid solution = new PlayableGameGrid();
		for (int corner=0; corner < solution.getWidth(); corner++)
		{
			// fill remainder of row
			for (int x=corner; x<solution.getWidth(); x++)
			{
				if (solution.getValue(x, corner) == SudokuConstants.VALUE_EMPTY)
				{
					int value = generateValueForSquare(solution, x, corner);
					if ((value == SudokuConstants.VALUE_EMPTY) || !solution.isValidValue(value))
					{
						throw new RuntimeException("Unable to generate value for squar in Rowe: X=" + x + " Y=" + corner + "\nPuzzle:\n" + solution);
					}
					solution.setValue(x, corner, value);
				}
			}
			
			// fill remainder of column
			for (int y=corner; y<solution.getWidth(); y++)
			{
				if (solution.getValue(corner, y) == SudokuConstants.VALUE_EMPTY)
				{
					int value = generateValueForSquare(solution, corner, y);
					if ((value == SudokuConstants.VALUE_EMPTY) || !solution.isValidValue(value))
					{
						throw new RuntimeException("Unable to generate value for square in Column: X=" + corner + " Y=" + y + "\nPuzzle:\n" + solution);
					}
					solution.setValue(corner, y, value);
				}
			}
		}
		
		if (!solution.isValidBoard())
		{
			throw new RuntimeException("Unable to create a valid game grid.  Initial grid that led to this error:\n" + solution);
		}
		if (!solution.isSolved())
		{
			throw new RuntimeException("Unable to create solvable game grid.  Initial grid that led to this error:\n" + solution);
		}
		
		Puzzle puzzle = new Puzzle(solution, solution);
		return puzzle;
	}
	
	private static int generateValueForSquare(GameGrid grid, int x, int y)
	{
		int v = SudokuConstants.VALUE_EMPTY;

		while (true)
		{
			v = (int)((double)(grid.getWidth()) * Math.random()) + 1;
			boolean isUnique = true;
			
			// is unique to row?
			for (int testX=0; testX<grid.getWidth(); testX++)
				if (grid.getValue(testX, y) == v)
				{
					isUnique = false;
					break;
				}

			// is unique to column?
			for (int testY=0; testY<grid.getHeight(); testY++)
				if (grid.getValue(x, testY) == v)
				{
					isUnique = false;
					break;
				}
			
			// is unique to quadrant?
			int quadStartX = (x / SudokuConstants.GRID_WIDTH) * SudokuConstants.GRID_WIDTH;
			int quadStartY = (y / SudokuConstants.GRID_WIDTH) * SudokuConstants.GRID_WIDTH;
			int quadEndX = quadStartX + SudokuConstants.GRID_WIDTH;
			int quadEndY = quadStartY + SudokuConstants.GRID_WIDTH;
			for (int testY=quadStartY; testY < quadEndY; testY++)
			{
				for (int testX=quadStartX; testX < quadEndX; testX++)
				{
					if (grid.getValue(testX, testY) == v)
					{
						isUnique = false;
						break;
					}
				}
			}

			if (isUnique)
				break;
		}
		
		return v;
	}
}
