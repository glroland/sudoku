package com.glroland.sudoku.exceptions;

import com.glroland.sudoku.model.GameGrid;

public class PuzzleGenerationDeadEndException extends RuntimeException {

	private static final long serialVersionUID = 5115400173215842342L;
	
	public PuzzleGenerationDeadEndException(GameGrid currentState)
	{
		this("", currentState);
	}
	
	public PuzzleGenerationDeadEndException(String msg, GameGrid currentState)
	{
		super("Encountered a dead end when generatinsg a new puzzle!\nCurrent Solution State:\n" + currentState.toString());
	}
	
	public PuzzleGenerationDeadEndException(String msg)
	{
		super("Encountered a dead end when generatinsg a new puzzle!  (No details passed to exception)");
	}
}
