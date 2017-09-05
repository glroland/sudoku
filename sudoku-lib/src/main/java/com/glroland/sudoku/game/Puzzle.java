package com.glroland.sudoku.game;

import java.util.ArrayList;

import com.glroland.sudoku.exceptions.IllegalMoveException;
import com.glroland.sudoku.model.GameGrid;
import com.glroland.sudoku.model.GameMove;
import com.glroland.sudoku.model.PlayableGameGrid;
import com.glroland.sudoku.util.SudokuConstants;

public class Puzzle {

	private GameGrid initialPuzzle;
	private GameGrid puzzleSolution;
	private PlayableGameGrid activeGrid;
	private ArrayList<GameMove> playerMoves;
	
	public Puzzle(GameGrid initial, GameGrid solution)
	{
		// confirm arguments
		if (initial == null)
			throw new IllegalArgumentException("Initial game grid provided is null!");
		if (solution == null)
			throw new IllegalArgumentException("Solution to puzzle is null!");
		
		// validate that the solution is complete
		if (!solution.isSolved())
			throw new IllegalArgumentException("Provided solution is not actually complete");
		
		// validate that the solution is accurate
		if (!solution.isValidBoard())
			throw new IllegalArgumentException("Provided solution is not a valid game board");
		
		// ensure that the initial puzzle is a derivative of the solution
		if (!solution.isDerivative(initial))
			throw new IllegalArgumentException("Initial game board is not a derivative of the solution");
		
		// store values
		initialPuzzle = initial;
		puzzleSolution = solution;
		activeGrid = new PlayableGameGrid(initialPuzzle);
		
		// reset the moves list
		playerMoves = new ArrayList<GameMove>();
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
	
	public void makeMove(int x, int y, int v)
	{
		// is the move allowed per the active grid playboard?
		if (!activeGrid.isValidMove(x, y, v))
		{
			throw new IllegalMoveException("Move attempted against active puzzle", x, y, v);
		}
		
		// is the move part of the initial game board?
		if (initialPuzzle.getValue(x,  y) != SudokuConstants.VALUE_EMPTY)
		{
			throw new IllegalMoveException("Unpermitted attempt to change part of the original game puzzle", x, y, v);			
		}
		
		activeGrid.setValue(x, y, v);
		
		// store the move
		GameMove move = new GameMove(x, y, v);
		playerMoves.add(move);
	}
	
	public boolean isCorrect()
	{
		return puzzleSolution.isDerivative(activeGrid);
	}
	
	public boolean isSolved()
	{
		return activeGrid.isSolved();
	}
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder("Solution:\n");
		sb.append(puzzleSolution.toString()).append("\n");
		sb.append("Initial Puzzle:\n");
		sb.append(initialPuzzle.toString()).append("\n");
		sb.append("Current State of Game Play:\n");
		sb.append(activeGrid.toString()).append("\n");
		
		return sb.toString();
	}
}
