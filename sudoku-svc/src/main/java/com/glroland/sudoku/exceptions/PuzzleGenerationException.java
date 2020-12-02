package com.glroland.sudoku.exceptions;

import com.glroland.sudoku.model.GameGrid;

public class PuzzleGenerationException extends RuntimeException {

	private static final long serialVersionUID = 5115400173215842342L;
	
	public PuzzleGenerationException(GameGrid currentState)
	{
		this("", currentState);
	}
	
	public PuzzleGenerationException(String msg, GameGrid currentState)
	{
		super("Encountered a dead end when generating a new puzzle!\nCurrent Solution State:\n" + currentState.toString());
	}
	
	public PuzzleGenerationException(String msg)
	{
		super("Encountered a dead end when generating a new puzzle!  (No details passed to exception)");
	}
}
