package com.glroland.sudoku.game;

import java.util.ArrayList;
import java.util.Random;

import com.glroland.sudoku.exceptions.PuzzleGenerationDeadEndException;
import com.glroland.sudoku.model.PlayableGameGrid;
import com.glroland.sudoku.util.SudokuConstants;

public class PuzzleFactory 
{
	@SuppressWarnings("rawtypes")
	private final ArrayList ALL_VALUES = new ArrayList();
	
	@SuppressWarnings("unchecked")
	public PuzzleFactory()
	{
		// create basic value array that can be cloned
		for (int i=1; i<= SudokuConstants.PUZZLE_WIDTH; i++)
			ALL_VALUES.add(i);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Puzzle createPuzzle()
	{		
		// create basic puzzle solution matrix
		int gridSize = SudokuConstants.PUZZLE_WIDTH * SudokuConstants.PUZZLE_HEIGHT;
		ArrayList [] grid = new ArrayList[gridSize];
		for (int i=0; i<gridSize; i++)
			grid[i] = (ArrayList)ALL_VALUES.clone();

		// randomly refine values, cell by cell
		randomlyPopulateCells(grid);
		PlayableGameGrid solution = createGridFromArray(grid);
			
		// validate solution from solution matrix
		if (!solution.isValidBoard())
		{
			throw new RuntimeException("Unable to create a valid game grid.  Initial grid that led to this error:\n" + solution);
		}
		if (!solution.isSolved())
		{
			throw new RuntimeException("Unable to create solvable game grid.  Initial grid that led to this error:\n" + solution);
		}
		
		Puzzle puzzle = new Puzzle(solution, solution);
		
		
		return puzzle;
	}
	
	@SuppressWarnings("rawtypes")
	private PlayableGameGrid createGridFromArray(ArrayList [] grid)
	{
		PlayableGameGrid solution = new PlayableGameGrid();
		for (int y=0; y<SudokuConstants.PUZZLE_HEIGHT; y++)
			for (int x=0; x<SudokuConstants.PUZZLE_WIDTH; x++)
			{
				ArrayList values = grid[x + (y * SudokuConstants.PUZZLE_WIDTH)];
				if ((values == null) || (values.size() == 0))
				{
//					throw new RuntimeException("While building solution object, encountered a null solution matrix entry at position: " + x + " ," + y);
					continue;
				}
				else if (values.size() != 1)
				{
					throw new RuntimeException("While building solution object, encountered a solution matrix entry at position (" + x + " ," + y + ") that had an unusual size, expected 1 but received: " + values.size());
				}
				int v = (Integer)values.get(0);
				solution.setValue(x, y, v);
			}
		
		return solution;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void randomlyPopulateCells(ArrayList [] grid)
	{
		int gridSize = grid.length;
		
		Random r = new Random();
		for (int i=0; i < gridSize; i++)
		{
			// randomly select one of the values in the list and make it active
			ArrayList values = grid[i];
			if ((values == null) || (values.size() == 0))
			{
				throw new PuzzleGenerationDeadEndException("While randomizing cell value, encountered a null or empty solution matrix entry at position: I=" + i);
			}
			int value;
			if (values.size() == 1)
				value = (Integer)values.get(0);
			else
			{
				value = (Integer)values.get(r.nextInt(values.size()));
				values.clear();
				values.add(value);
			}
			
			// remove it from every cell in the row
			int endI = ((i / SudokuConstants.PUZZLE_WIDTH) * SudokuConstants.PUZZLE_WIDTH) + SudokuConstants.PUZZLE_WIDTH - 1;
			for (int clear = i + 1; clear <= endI; clear++)
			{
				ArrayList vi = grid[clear];
				removeValueFromList(vi, value);
			}
			
			// remove it from every cell in the column
			for (int clear = i + SudokuConstants.PUZZLE_WIDTH; clear < gridSize; clear += SudokuConstants.PUZZLE_WIDTH)
			{
				ArrayList vi = grid[clear];
				removeValueFromList(vi, value);				
			}
			
			// remove it from every cell in the quadrant
			int x = i % SudokuConstants.PUZZLE_WIDTH;
			int y = i / SudokuConstants.PUZZLE_WIDTH;
			int gridStartX = (x / SudokuConstants.GRID_WIDTH) * SudokuConstants.GRID_WIDTH;
			int gridStartY = (y / SudokuConstants.GRID_WIDTH) * SudokuConstants.GRID_WIDTH;
			int gridEndX = gridStartX + SudokuConstants.GRID_WIDTH - 1;
			int gridEndY = gridStartY + SudokuConstants.GRID_WIDTH - 1;
			for (int gy = gridStartY; gy <= gridEndY; gy++)
				for (int gx = gridStartX; gx <= gridEndX; gx++)
				{
					if ((gy != y) && (gx != x))
					{
						ArrayList vi = grid[gx + (gy * SudokuConstants.PUZZLE_WIDTH)];
						removeValueFromList(vi, value);
					}
				}
		}
	}
	
	@SuppressWarnings("rawtypes")
	private void removeValueFromList(ArrayList values, int v)
	{
		if ((values != null) && (values.size() != 0))
		{		
			int remove = -1;
			for (int i=0; i < values.size(); i++)
			{
				int iv = (Integer)values.get(i);
				if (iv == v)
				{
					remove = i;
					break;
				}
			}
			
			if (remove != -1)
			{
				values.remove(remove);
			}
		}
	}
}
