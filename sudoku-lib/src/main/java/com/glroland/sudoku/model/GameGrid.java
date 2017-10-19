package com.glroland.sudoku.model;

import com.glroland.sudoku.exceptions.InvalidPositionException;
import com.glroland.sudoku.util.SudokuConstants;

public class GameGrid {

	private final int width = SudokuConstants.PUZZLE_WIDTH;
	private final int height = SudokuConstants.PUZZLE_HEIGHT;
	protected int [] grid = null;

	public GameGrid()
	{
		grid = new int[width * height];
		for (int x=0; x<width; x++)
		{
			for (int y=0; y<height; y++)
			{
				grid[x + (y*width)] = SudokuConstants.VALUE_EMPTY;
			}
		}
	}
	
	public GameGrid(int [] gcopy)
	{
		if ((gcopy == null) || (gcopy.length != (width * height)))
		{
			throw new IllegalArgumentException("Input game grid passed to GameGrid constructor is invalid");
		}
		
		grid = gcopy;
//		System.arraycopy(gcopy, 0, grid, 0, grid.length);
	}
	
	public boolean isValidLocation(int x, int y)
	{
		if ((x < 0) || (x >= width))
			return false;
		
		if ((y < 0) || (y >= height))
			return false;
		
		return true;
	}
	
	public Object clone()
	{
		int [] gridCopy = grid.clone();
		return new GameGrid(gridCopy);
	}
	
	public boolean equals(Object in)
	{
		if (in == null)
			return false;
		if (!(in instanceof GameGrid))
			return false;
		GameGrid inGame = (GameGrid)in;
		if (inGame.grid.length != grid.length)
			return false;
		for (int i=0; i<grid.length; i++)
			if (inGame.grid[i] != grid[i])
				return false;
		
		return true;
	}
	
	public boolean isValidMove(int x, int y, int v)
	{
		// validation location first
		if (!isValidLocation(x, y))
			return false;
		
		// empty always allowed
		if (v == SudokuConstants.VALUE_EMPTY)
			return true;
		
		// check row
		for (int testX=0; testX < getWidth(); testX++)
		{
			if (testX != x)
			{
				if (grid[testX + (y*width)] == v)
					return false;
			}
		}
		
		// check column
		for (int testY=0; testY < getHeight(); testY++)
		{
			if (testY != y)
			{
				if (grid[x + (testY*width)] == v)
					return false;
			}
		}
		
		// check grid quadrant
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
					if (grid[testX + (testY*width)] == v)
						return false;
				}
			}
		}
		
		return true;
	}

	public boolean isValidBoard()
	{
		for (int y=0; y<height; y++)
		{
			for (int x=0; x<width; x++)
			{
				if (!isValidMove(x, y, getValue(x, y)))
					return false;
			}
		}
		
		return true;
	}
	
	public boolean isSolved()
	{
		for (int y=0; y<height; y++)
		{
			for (int x=0; x<width; x++)
			{
				if (grid[x + (y*width)] == SudokuConstants.VALUE_EMPTY)
					return false;
			}
		}
		
		return true;
	}
	
	public int getValue(int x, int y)
	{
		if (!isValidLocation(x, y))
			throw new InvalidPositionException(x, y, "Bad location passed to getValue");
		
		return grid[x + (y*width)];
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public boolean isDerivative(GameGrid childGrid)
	{
		// validate arguments
		if (childGrid == null)
			return false;
		
		// sizes match?
		if (childGrid.width != this.width)
			return false;
		if (childGrid.height != this.height)
			return false;
		
		// check each populated square of the child puzzle to ensure all filled values match this/parent
		for (int y=0; y<height; y++)
		{
			for (int x=0; x<width; x++)
			{
				int childValue = childGrid.grid[x+(y*width)];
				if (childValue != SudokuConstants.VALUE_EMPTY)
				{
					if (childValue != grid[x+(y*width)])
						return false;
				}
			}
		}
		
		return true;
	}
	
	public String toString()
	{
		StringBuilder s = new StringBuilder();
		for (int i=0; i<grid.length; i++)
		{
			// convert array into relative positioning
			int x = i % width;
			int y = i / width;
			int v = grid[i];
			
			if ((x > 0) && ((x % SudokuConstants.GRID_WIDTH) == 0))
				s.append("|");
			s.append(v);
			if (x == (width - 1))
			{
				s.append("\n");
				if ((y > 0) && (y < (height - 1)) && ((y % SudokuConstants.GRID_WIDTH) == (SudokuConstants.GRID_WIDTH-1)))
					s.append("-----------\n");
			}
		}
		return s.toString();
	}
}
