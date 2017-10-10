package com.glroland.sudoku.game;

import java.util.ArrayList;
import java.util.Random;

import com.glroland.sudoku.exceptions.PuzzleGenerationException;
import com.glroland.sudoku.model.PlayableGameGrid;
import com.glroland.sudoku.util.SudokuConstants;

public class PuzzleFactory 
{
	private class PuzzleState
	{
		private ArrayList [] grid = null;
		
		public int chosenSequence = -1;
		public int chosenValue = -1;
		public ArrayList oldList = null;
		
		public boolean deadEnd = false;
		
		public void setGrid(ArrayList [] gridIn)
		{
			if (gridIn == null)
				throw new IllegalArgumentException("Input grid array list is null");
			
			grid = new ArrayList[gridIn.length];
			for (int i=0; i<gridIn.length; i++)
			{
				grid[i] = new ArrayList();
				for (int v=0; v<gridIn[i].size(); v++)
					grid[i].add(gridIn[i].get(v));
			}
		}
		
		public ArrayList [] getGrid()
		{
			return grid;
		}
	}
	
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

		// recursive puzzle generation
		PuzzleState state = new PuzzleState();
		state.setGrid(grid);
		state = populatePuzzle(state);
		grid = state.getGrid();
				
		// validate solution from solution matrix
		PlayableGameGrid solution = createGridFromArray(state.getGrid());
		if (!solution.isValidBoard())
		{
			throw new RuntimeException("Unable to create a valid game grid.  Initial grid that led to this error:\n" + solution);
		}
		if (!solution.isSolved())
		{
			throw new RuntimeException("Unable to create solvable game grid.  Initial grid that led to this error:\n" + solution);
		}

		// create puzzle object now that we have a valid game grid
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
	
	private PuzzleState populatePuzzle(PuzzleState priorState)
	{
		// stage a clean state for new iteration
		PuzzleState state = new PuzzleState();
		state.setGrid(priorState.getGrid());
		
		// randomly refine values, cell by cell
		boolean result = populateCell(state);
		if (result)  // success
		{
			// rinse and repeat
			state = populatePuzzle(state);
		}
		else
		{
			// trip dead end
			state.deadEnd = true;
		}
		
		if(state.deadEnd) 
		{
			if ((state.oldList != null) && (state.oldList.size() > 1))
			{
				// -- remove "bad try" and try again
				state.oldList.remove(state.chosenSequence);
				state.deadEnd = false;
				state = populatePuzzle(state);		
			}
		}
		
		return state;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private boolean populateCell(PuzzleState state)
	{
		int gridSize = state.getGrid().length;
		
		// cleanse 1 values and identify dead ends first
		for (int i=0; i < gridSize; i++)
		{
			// randomly select one of the values in the list and make it active
			ArrayList values = state.getGrid()[i];
			if ((values == null) || (values.size() == 0))
			{
				return false;
//				throw new PuzzleGenerationException("While randomizing cell value, encountered a null or empty solution matrix entry at position: I=" + i);
			}
			if (values.size() == 1)
			{
				int value = (Integer)values.get(0);
				cleanseValue(state.getGrid(), value, i);
			}
		}

		// make one "decision" per invocation
		Random r = new Random();
		for (int i=0; i < gridSize; i++)
		{
			// randomly select one of the values in the list and make it active
			ArrayList values = state.getGrid()[i];
			if (values.size() > 1)
			{
				int seq = r.nextInt(values.size());
				state.chosenSequence = seq;
				int value = (Integer)values.get(seq);
				state.chosenValue = value;
				state.oldList = (ArrayList)values.clone();
				values.clear();
				values.add(value);
				cleanseValue(state.getGrid(), value, i);
				
				break;
			}
		}
		
		return true;
	}
	
	private void cleanseValue(ArrayList [] grid, int value, int i)
	{
		// remove it from every cell in the row
		int endI = ((i / SudokuConstants.PUZZLE_WIDTH) * SudokuConstants.PUZZLE_WIDTH) + SudokuConstants.PUZZLE_WIDTH - 1;
		for (int clear = i + 1; clear <= endI; clear++)
		{
			ArrayList vi = grid[clear];
			removeValueFromList(vi, value);
		}
		
		// remove it from every cell in the column
		for (int clear = i + SudokuConstants.PUZZLE_WIDTH; clear < grid.length; clear += SudokuConstants.PUZZLE_WIDTH)
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
