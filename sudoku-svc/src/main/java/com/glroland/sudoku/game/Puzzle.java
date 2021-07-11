package com.glroland.sudoku.game;

import java.util.ArrayList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glroland.sudoku.exceptions.IllegalMoveException;
import com.glroland.sudoku.model.GameGrid;
import com.glroland.sudoku.model.GameMove;
import com.glroland.sudoku.model.PlayableGameGrid;
import com.glroland.sudoku.util.SudokuConstants;

public class Puzzle {

	private static final Log log = LogFactory.getLog(Puzzle.class);

	private GameGrid initialPuzzle;
	private GameGrid puzzleSolution;
	private PlayableGameGrid activeGrid;
	private ArrayList<GameMove> playerMoves;
	
	public Puzzle(GameGrid initial, GameGrid solution)
	{
		// confirm arguments
		if (initial == null)
		{
			String msg = "Initial game grid provided is null!";
			log.error(msg);
			throw new IllegalArgumentException(msg);
		}
		if (solution == null)
		{
			String msg = "Solution to puzzle is null!";
			log.error(msg);
			throw new IllegalArgumentException(msg);
		}
		
		// validate that the solution is complete
		if (!solution.isSolved())
		{
			String msg = "Provided solution is not actually complete";
			log.error(msg);
			throw new IllegalArgumentException(msg);
		}
		
		// validate that the solution is accurate
		if (!solution.isValidBoard())
		{
			String msg = "Provided solution is not a valid game board";
			log.error(msg);
			throw new IllegalArgumentException(msg);
		}
		
		// ensure that the initial puzzle is a derivative of the solution
		if (!solution.isDerivative(initial))
		{
			String msg = "Initial game board is not a derivative of the solution";
			log.error(msg);
			throw new IllegalArgumentException(msg);
		}
		
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
			String msg = "Move attempted against active puzzle";
			log.error(msg);
			throw new IllegalMoveException(msg, x, y, v);
		}
		
		// is the move part of the initial game board?
		if (initialPuzzle.getValue(x,  y) != SudokuConstants.VALUE_EMPTY)
		{
			String msg = "Unpermitted attempt to change part of the original game puzzle";
			log.error(msg);
			throw new IllegalMoveException(msg, x, y, v);			
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
