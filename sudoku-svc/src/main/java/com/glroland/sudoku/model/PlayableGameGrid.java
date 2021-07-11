package com.glroland.sudoku.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glroland.sudoku.exceptions.InvalidPositionException;
import com.glroland.sudoku.exceptions.InvalidValueException;
import com.glroland.sudoku.util.SudokuConstants;

public class PlayableGameGrid extends GameGrid 
{
	private static final Log log = LogFactory.getLog(PlayableGameGrid.class);

	public PlayableGameGrid()
	{
		super();
	}
	
	public PlayableGameGrid(GameGrid g)
	{
		if (g == null)
		{
			String msg = "Input value g passed to clone constructor is null!";
			log.error(msg);
			throw new IllegalArgumentException(msg);
		}
		
		if (g.grid.length != grid.length)
		{
			String msg = "Input GameGrid to clone is not of the same size as the current game grid. grid.length=" + grid.length + "  g.grid.length=" + g.grid.length;
			log.error(msg);
			throw new IllegalArgumentException(msg);
		}
		
		System.arraycopy(g.grid, 0, grid, 0, g.grid.length);
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
		{
			String msg = "Bad location passed to setValue.  Value specified=" + v;
			log.error(msg);
			throw new InvalidPositionException(x, y, msg);
		}
		
		if (!isValidValue(v))
		{
			String msg = "Bad value passed to setValue.  Location specified=(" + x + ", " + y + ")";
			log.error(msg);
			throw new InvalidValueException(v, msg);
		}
		
		grid[x + (y*getWidth())] = v;
	}
}
