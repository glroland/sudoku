package com.glroland.sudoku.game;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glroland.sudoku.exceptions.PuzzleGenerationException;
import com.glroland.sudoku.exceptions.UnsolvablePuzzleException;
import com.glroland.sudoku.model.GameGrid;
import com.glroland.sudoku.model.PlayableGameGrid;
import com.glroland.sudoku.util.SudokuConstants;

public class PuzzleFactory 
{	
	private static final Log log = LogFactory.getLog(PuzzleFactory.class);

	private Random r = new Random();
	
	private final int COMPLEXITY_COUNTER = 100;
	
	public Puzzle createPuzzle()
	{
		// create starter grid
		PuzzleCreationGameGrid inputGrid = new PuzzleCreationGameGrid();
		for (int i=0; i<SudokuConstants.PUZZLE_BLOCK_COUNT; i++)
			inputGrid.getMoveList(i).addAll(SudokuConstants.ALL_VALUES);

		// generate solved puzzle
		PuzzleCreationGameGrid outputGrid = recursiveGeneration(0, inputGrid);		
		if (outputGrid == null)
		{
			String msg = "Puzzle generation algorithm returned a null solution object!";
			log.error(msg);
			throw new PuzzleGenerationException(msg);
		}
		GameGrid solution = outputGrid.getResultingGrid();
		if (!solution.isValidBoard())
		{
			String msg = "Invalid board created by puzzle generator";
			log.error(msg);
			throw new PuzzleGenerationException(msg, solution);
		}
		if (!solution.isSolved())
		{
			String msg = "Incomplete game solution created by puzzle generator";
			log.error(msg);
			throw new PuzzleGenerationException(msg, solution);
		}
		
		// create game grid
		GameGrid gameBoard = (GameGrid)solution.clone();
		for (int counter=0; counter < COMPLEXITY_COUNTER; counter++)
			gameBoard = recursiveDeteriorate(gameBoard, solution);
		
		// create puzzle object now that we have a valid game grid
		Puzzle puzzle = new Puzzle(gameBoard, solution);
		return puzzle;

	}	
	
	private GameGrid recursiveDeteriorate(GameGrid snapshot, GameGrid solution)
	{
		// validate arguments
		if (snapshot == null)
		{
			String msg = "Input Snapshot GameGrid object is null!";
			log.error(msg);
			throw new IllegalArgumentException(msg);
		}
		if (solution == null)
		{
			String msg = "Input Solution GameGrid object is null!";
			log.error(msg);
			throw new IllegalArgumentException(msg);
		}
		
		// randomly remove a square
		PlayableGameGrid modifiable = new PlayableGameGrid(snapshot);
		int pos = 0;
		int x = 0;
		int y = 0;
		for (int counter=0; counter < COMPLEXITY_COUNTER; counter++)
		{
			pos = r.nextInt(SudokuConstants.PUZZLE_BLOCK_COUNT);
			x = pos % SudokuConstants.PUZZLE_WIDTH;
			y = pos / SudokuConstants.PUZZLE_WIDTH;
			if (modifiable.getValue(x, y) != SudokuConstants.VALUE_EMPTY)
				break;
		}
		modifiable.setValue(x, y, SudokuConstants.VALUE_EMPTY);
		
		try
		{
			// attempt to solve
			GameGrid modifiableSolution = Solver.createSolution(modifiable);
			if (modifiableSolution != null)
			{
				if (modifiableSolution.equals(solution))
				{
					GameGrid deeperGame = recursiveDeteriorate(modifiable, solution);
					if (deeperGame == null)
						return modifiable;
					else
					{
						GameGrid deeperSolution = Solver.createSolution(deeperGame);
						if (deeperSolution != null)
						{
							if (deeperSolution.equals(solution))
								return deeperGame;
						}
						return modifiable;
					}
				}
			}
		} 
		catch (UnsolvablePuzzleException e)
		{
			return snapshot;
		}
		
		return null;
	}
	
	private PuzzleCreationGameGrid recursiveGeneration(int pos, PuzzleCreationGameGrid inputGrid)
	{
		// validate arguments
		if (inputGrid == null)
		{
			String msg = "Input game grid is null and was passed to the generation algorithm";
			log.error(msg);
			throw new IllegalArgumentException(msg);
		}
		if (pos < 0)
		{
			String msg = "Input position counter is negative!";
			log.error(msg);
			throw new IllegalArgumentException(msg);
		}
		if (pos >= SudokuConstants.PUZZLE_BLOCK_COUNT)
		{
			String msg = "Input position counter is larger than game board!";
			log.error(msg);			
			throw new IllegalArgumentException(msg);			
		}
		
		// recursion complete
		if (inputGrid.isFullyPopulated())
		{
			GameGrid grid = inputGrid.getResultingGrid();
			if (grid.isValidBoard())
				return inputGrid;
			else
				return null;
		}
		
		// current spot
		LinkedList<Integer> moves = (LinkedList<Integer>)inputGrid.getMoveList(pos).clone();
		Collections.shuffle(moves, r);
		for (Integer value : moves)
		{
			PuzzleCreationGameGrid tempGrid = (PuzzleCreationGameGrid)inputGrid.clone();
			tempGrid.getMoveList(pos).clear();
			tempGrid.getMoveList(pos).add(value);
			tempGrid.cleanseValue(value, pos);
			
			if (tempGrid.isFullyPopulated())
			{
				GameGrid board = tempGrid.getResultingGrid();
				if (board.isValidBoard())
					return tempGrid;
			}
			else
			{
				if (pos < (SudokuConstants.PUZZLE_BLOCK_COUNT - 1))
				{
					tempGrid = recursiveGeneration(pos + 1, tempGrid);
					if (tempGrid != null)
					{
						GameGrid board = tempGrid.getResultingGrid();
						if (board.isValidBoard())
							return tempGrid;
					}
				}				
			}
			
		}
		
		return null;
	}
}
