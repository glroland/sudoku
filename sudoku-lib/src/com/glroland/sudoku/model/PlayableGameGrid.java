package com.glroland.sudoku.model;

import com.glroland.sudoku.exceptions.InvalidPositionException;
import com.glroland.sudoku.exceptions.InvalidValueException;
import com.glroland.sudoku.util.SudokuConstants;

public class PlayableGameGrid extends GameGrid {

	public PlayableGameGrid()
	{
		super();
	}
	
	public PlayableGameGrid(GameGrid g)
	{
		if (g == null)
			throw new IllegalArgumentException("Input value g passed to clone constructor is null!");
		
		if (g.grid.length != grid.length)
			throw new IllegalArgumentException("Input GameGrid to clone is not of the same size as the current game grid. grid.length=" + grid.length + "  g.grid.length=" + g.grid.length);
		
		for (int p=0; p<g.grid.length; p++)
			grid[p] = g.grid[p];
	}
	
	public boolean isValidValue(int v)
	{
		if (v == SudokuConstants.VALUE_EMPTY)
			return true;
		
		if ((v >= SudokuConstants.VALUE_MIN) && (v <= SudokuConstants.VALUE_MAX))
			return true;
		
		return false;
	}
	
	public void setValue(int x, int y, int v)
	{
		if (!isValidLocation(x, y))
			throw new InvalidPositionException(x, y, "Bad location passed to setValue.  Value specified=" + v);
		
		if (!isValidValue(v))
			throw new InvalidValueException(v, "Bad value passed to setValue.  Location specified=(" + x + ", " + y + ")");
		
		grid[x + (y*getWidth())] = v;
	}
}
