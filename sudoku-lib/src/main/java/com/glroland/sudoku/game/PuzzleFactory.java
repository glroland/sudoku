package com.glroland.sudoku.game;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

import com.glroland.sudoku.exceptions.PuzzleGenerationException;
import com.glroland.sudoku.model.GameGrid;
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
		
		// create puzzle object now that we have a valid game grid
		Puzzle puzzle = new Puzzle(solution, solution);
		
		
		return puzzle;

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
