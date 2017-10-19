package com.glroland.sudoku.game;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

import com.glroland.sudoku.exceptions.PuzzleGenerationException;
import com.glroland.sudoku.exceptions.UnsolvablePuzzleException;
import com.glroland.sudoku.model.GameGrid;
import com.glroland.sudoku.model.PlayableGameGrid;
import com.glroland.sudoku.util.SudokuConstants;

public class PuzzleFactory 
{	
	private Random r = new Random();
	
	public Puzzle createPuzzle()
	{
		// create starter grid
		PuzzleCreationGameGrid inputGrid = new PuzzleCreationGameGrid();
		for (int i=0; i<SudokuConstants.PUZZLE_BLOCK_COUNT; i++)
			inputGrid.getMoveList(i).addAll(SudokuConstants.ALL_VALUES);

		// generate solved puzzle
		PuzzleCreationGameGrid outputGrid = recursiveGeneration(0, inputGrid);		
		if (outputGrid == null)
			throw new PuzzleGenerationException("Puzzle generation algorithm returned a null solution object!");
		GameGrid solution = outputGrid.getResultingGrid();
		if (!solution.isValidBoard())
			throw new PuzzleGenerationException("Invalid board created by puzzle generator", solution);
		if (!solution.isSolved())
			throw new PuzzleGenerationException("Incomplete game solution created by puzzle generator", solution);
		
		// create game grid
		GameGrid gameBoard = (GameGrid)solution.clone();
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
			throw new IllegalArgumentException("Input Snapshot GameGrid object is null!");
		}
		if (solution == null)
		{
			throw new IllegalArgumentException("Input Solution GameGrid object is null!");
		}
		
		// randomly remove a square
		int pos = r.nextInt(SudokuConstants.PUZZLE_BLOCK_COUNT);
		int x = pos % SudokuConstants.PUZZLE_WIDTH;
		int y = pos / SudokuConstants.PUZZLE_WIDTH;
		PlayableGameGrid modifiable = new PlayableGameGrid(snapshot);
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
			throw new IllegalArgumentException("Input game grid is null and was passed to the generation algorithm");
		}
		if (pos < 0)
		{
			throw new IllegalArgumentException("Input position counter is negative!");
		}
		if (pos >= SudokuConstants.PUZZLE_BLOCK_COUNT)
		{
			throw new IllegalArgumentException("Input position counter is larger than game board!");			
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
