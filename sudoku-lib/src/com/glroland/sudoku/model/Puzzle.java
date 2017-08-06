package com.glroland.sudoku.model;

public class Puzzle {

	private GameGrid initialPuzzle;
	private GameGrid puzzleSolution;
	private PlayableGameGrid activeGrid;
	
	public Puzzle(GameGrid initial, GameGrid solution)
	{
		// confirm arguments
		if (initial == null)
			throw new IllegalArgumentException("Initial game grid provided is null!");
		if (solution == null)
			throw new IllegalArgumentException("Solution to puzzle is null!");
		
		// validate that the solution is complete
		if (solution.isSolved())
			throw new IllegalArgumentException("Provided solution is not actually complete");
		
		// validate that the solution is accurate
		if (!solution.isValidBoard())
			throw new IllegalArgumentException("Provided solution is not a valid game board");
		
		// ensure that the initial puzzle is a derivative of the solution
		if (solution.isDerivative(initial))
			throw new IllegalArgumentException("Initial game board is not a derivative of the solution");
		
		// store values
		initialPuzzle = initial;
		puzzleSolution = solution;
		activeGrid = new PlayableGameGrid(initialPuzzle);
	}
	
	public GameGrid getPuzzleSolution()
	{
		return puzzleSolution;
	}
	
	public GameGrid getInitialPuzzle()
	{
		return initialPuzzle;
	}
	
	public GameGrid getActiveGrid()
	{
		return activeGrid;
	}
}
