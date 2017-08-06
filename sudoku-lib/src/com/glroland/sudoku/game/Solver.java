package com.glroland.sudoku.game;

import java.util.ArrayList;
import java.util.List;

import com.glroland.sudoku.model.GameGrid;
import com.glroland.sudoku.model.GameMove;
import com.glroland.sudoku.model.PlayableGameGrid;
import com.glroland.sudoku.util.SudokuConstants;

public class Solver {

	public static GameGrid createSolution(GameGrid grid)
	{
		// validate input grid first
		if (grid == null)
			throw new IllegalArgumentException("Input grid passed to solver is null!");
		if (!grid.isValidBoard())
			throw new IllegalArgumentException("Input game board is not valid - hence no solution is possible");

		PlayableGameGrid playGrid = new PlayableGameGrid(grid);
		while (!playGrid.isSolved())
		{
			// are there any next moves?
			List<GameMove> nextMoves = getNextMovesForPuzzle(playGrid);
			if (nextMoves.size() == 0)
				throw new RuntimeException("Unable to solve game board!\nInput:\n" + grid.toString() + "\nCurrent Solution State:\n" + playGrid.toString());
			
			// make each of the recommended next moves
			for (GameMove move : nextMoves)
			{
				playGrid.setValue(move.getX(), move.getY(), move.getValue());
			}			
		}

		return playGrid;
	}
	
	public static List<GameMove> getNextMovesForPuzzle(GameGrid grid)
	{
		// validate input grid first
		if (grid == null)
			throw new IllegalArgumentException("Input grid passed to solver is null!");
		if (!grid.isValidBoard())
			throw new IllegalArgumentException("Input game board is not valid - hence no solution is possible");
		
		ArrayList<GameMove> list = new ArrayList<GameMove>();
		if (!grid.isSolved())
		{
			// for each square
			for (int y=0; y<grid.getHeight(); y++)
			{
				for (int x=0; x<grid.getWidth(); x++)
				{
					List<Integer> values = getValidValuesForSquare(grid, x, y);
					if (values.size() == 1)
					{
						GameMove move = new GameMove(x, y, values.get(0));
						list.add(move);
					}
				}
			}
			
		}
		
		return list;
	}
	
	public static List<Integer> getValidValuesForSquare(GameGrid grid, int x, int y)
	{
		// validate input grid first
		if (grid == null)
			throw new IllegalArgumentException("Input grid passed to solver is null!");
		if (!grid.isValidBoard())
			throw new IllegalArgumentException("Input game board is not valid - hence no solution is possible");
		
		ArrayList<Integer> validValues = new ArrayList<Integer>();
		for (int i=SudokuConstants.VALUE_MIN; i<=SudokuConstants.VALUE_MAX; i++)
		{
			boolean isValid = true;
			
			// disqualify by current row first
			for (int testX=0; testX<grid.getWidth(); testX++)
			{
				if (testX != x)
				{
					int gridValue = grid.getValue(testX, y);
					if (gridValue == i)
					{
						isValid = false;
						break;
					}
				}
			}
			
			// disqualify by current column next
			for (int testY=0; testY<grid.getHeight(); testY++)
			{
				if (testY != y)
				{
					int gridValue = grid.getValue(x, testY);
					if (gridValue == i)
					{
						isValid = false;
						break;
					}
				}
			}		
			
			// disqualify by quadrant last
			int quadStartX = (x / SudokuConstants.GRID_WIDTH) * SudokuConstants.GRID_WIDTH;
			int quadStartY = (y / SudokuConstants.GRID_WIDTH) * SudokuConstants.GRID_WIDTH;
			int quadEndX = quadStartX + SudokuConstants.GRID_WIDTH;
			int quadEndY = quadStartY + SudokuConstants.GRID_WIDTH;
			for (int testY=quadStartY; testY < quadEndY; testY++)
			{
				for (int testX=quadStartX; testX < quadEndX; testX++)
				{
					if ((testX != x) && (testY != y))
					{
						int gridValue = grid.getValue(testX, testY);
						if (gridValue == i)
						{
							isValid = false;
							break;
						}
					}
				}
			}
			
			if (isValid)
				validValues.add(i);
		}
		return validValues;
	}
}
