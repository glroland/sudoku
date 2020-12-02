package com.glroland.sudoku.exceptions;

import com.glroland.sudoku.model.GameGrid;

public class UnsolvablePuzzleException extends RuntimeException {

	private static final long serialVersionUID = 94780555612566604L;

	public UnsolvablePuzzleException(GameGrid originalGrid, GameGrid currentState)
	{
		this("", originalGrid, currentState);
	}
	
	public UnsolvablePuzzleException(String msg, GameGrid originalGrid, GameGrid currentState)
	{
		super("Unable to solve game board!\nInput:\n" + originalGrid.toString() + "\nCurrent Solution State:\n" + currentState.toString());
	}
}
