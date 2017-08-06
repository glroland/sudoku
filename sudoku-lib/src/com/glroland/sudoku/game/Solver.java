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
	
	private static ArrayList<List<Integer>> buildValidValuesGridForPuzzle(GameGrid grid)
	{
		// build the "grid"
		ArrayList<List<Integer>> moveGrid = new ArrayList<List<Integer>>();
		for (int y=0; y<grid.getHeight(); y++)
		{
			for (int x=0; x<grid.getWidth(); x++)
			{
				moveGrid.add(getValidValuesForSquare(grid, x, y));
			}
		}
		
		return moveGrid;
	}
	
	public static List<GameMove> getNextMovesForPuzzle(GameGrid grid)
	{
		// validate input grid first
		if (grid == null)
			throw new IllegalArgumentException("Input grid passed to solver is null!");
		if (!grid.isValidBoard())
			throw new IllegalArgumentException("Input game board is not valid - hence no solution is possible\n" + grid.toString());
		
		ArrayList<GameMove> list = new ArrayList<GameMove>();
		if (!grid.isSolved())
		{
			// get valid values
			ArrayList<List<Integer>> validValuesGrid = buildValidValuesGridForPuzzle(grid);
			validValuesGrid = refineValidValuesGrid(grid, validValuesGrid);
			
			// for each square
			for (int i=0; i<validValuesGrid.size(); i++)
			{
				int y = i / grid.getWidth();
				int x = i % grid.getWidth();
				
				List<Integer> values = validValuesGrid.get(i);
				if (values.size() == 1)
				{
					GameMove move = new GameMove(x, y, values.get(0));
					list.add(move);
				}
			}
		}
		
		return list;
	}
	
	private static ArrayList<List<Integer>> refineValidValuesGrid(GameGrid grid, ArrayList<List<Integer>> validValuesGrid)
	{
		ArrayList<List<Integer>> refinedGrid = new ArrayList<List<Integer>>();
		
		// analyze each cell
		int i=0;
		for (List<Integer> values : validValuesGrid)
		{
			ArrayList<Integer> refinedValues = new ArrayList<Integer>();
		
			int x = i % grid.getWidth();
			int y = i / grid.getWidth();
			
			for (int value : values)
			{
				boolean isUnique = isUniqueToRow(grid, validValuesGrid, x, y, value) 
								|| isUniqueToColumn(grid, validValuesGrid, x, y, value) 
								|| isUniqueToQuadrant(grid, validValuesGrid, x, y, value);
				if (isUnique)
				{
					refinedValues.add(value);
				}
			}
			
			refinedGrid.add(refinedValues);
			i++;
		}

		return refinedGrid;
	}
	
	private static boolean isUniqueToRow(GameGrid grid, ArrayList<List<Integer>> validValuesGrid, int x, int y, int v)
	{
		int i = y * grid.getWidth();
		for (int testX=0; testX < grid.getWidth(); testX++)
		{
			if (testX != x)
			{
				List<Integer> validValues = validValuesGrid.get(i + testX);
				for (Integer testV : validValues)
				{
					if (testV.equals(v))
						return false;
				}
			}
		}
		
		return true;
	}
	
	private static boolean isUniqueToColumn(GameGrid grid, ArrayList<List<Integer>> validValuesGrid, int x, int y, int v)
	{
		int i = x;
		for (int testY=0; testY < grid.getHeight(); testY++)
		{
			if (testY != y)
			{
				List<Integer> validValues = validValuesGrid.get(i + (testY*grid.getWidth()));
				for (Integer testV : validValues)
				{
					if (testV.equals(v))
						return false;
				}
			}
		}
		
		return true;
	}
	
	private static boolean isUniqueToQuadrant(GameGrid grid, ArrayList<List<Integer>> validValuesGrid, int x, int y, int v)
	{
		int quadStartX = (x / SudokuConstants.GRID_WIDTH) * SudokuConstants.GRID_WIDTH;
		int quadStartY = (y / SudokuConstants.GRID_WIDTH) * SudokuConstants.GRID_WIDTH;
		int quadEndX = quadStartX + SudokuConstants.GRID_WIDTH;
		int quadEndY = quadStartY + SudokuConstants.GRID_WIDTH;
		for (int testY=quadStartY; testY < quadEndY; testY++)
		{
			for (int testX=quadStartX; testX < quadEndX; testX++)
			{
				if (! ((testX == x) && (testY == y)) )
				{
					List<Integer> validValues = validValuesGrid.get(testX + (testY*grid.getWidth()));
					for (Integer testV : validValues)
					{
						if (testV.equals(v))
							return false;
					}
				}
			}
		}
	
		return true;
	}
	
	public static List<Integer> getValidValuesForSquare(GameGrid grid, int x, int y)
	{
		// validate input grid first
		if (grid == null)
			throw new IllegalArgumentException("Input grid passed to solver is null!");
		if (!grid.isValidBoard())
			throw new IllegalArgumentException("Input game board is not valid - hence no solution is possible");

		ArrayList<Integer> validValues = new ArrayList<Integer>();

		// populated squares assume valid values - do not reassess non-empty values
		if (grid.getValue(x, y) == SudokuConstants.VALUE_EMPTY)
		{
			// brute force test per square
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
		}
		
		return validValues;
	}
}
