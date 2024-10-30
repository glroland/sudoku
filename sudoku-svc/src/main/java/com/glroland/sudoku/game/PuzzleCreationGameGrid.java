package com.glroland.sudoku.game;

import java.util.LinkedList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glroland.sudoku.model.GameGrid;
import com.glroland.sudoku.util.SudokuConstants;

public class PuzzleCreationGameGrid implements Cloneable
{
	private static final Log log = LogFactory.getLog(PuzzleCreationGameGrid.class);

	@SuppressWarnings("unchecked")
	private LinkedList<Integer> [] grid = new LinkedList[SudokuConstants.PUZZLE_BLOCK_COUNT];
	
	public PuzzleCreationGameGrid()
	{
		for (int i = 0; i < grid.length; i++)
			grid[i] = new LinkedList<Integer>();
	}
	
	public LinkedList<Integer> getMoveList(int i)
	{
		return grid[i];
	}
	
	public LinkedList<Integer> getMoveList(int x, int y)
	{
		return grid[x + (y*SudokuConstants.PUZZLE_WIDTH)];		
	}

	public void cleanseValue(int val, int pivot)
	{
		Integer value = Integer.valueOf(val);
		
		// remove it from every cell in the row
		int endI = ((pivot / SudokuConstants.PUZZLE_WIDTH) * SudokuConstants.PUZZLE_WIDTH) + SudokuConstants.PUZZLE_WIDTH - 1;
		for (int clear = pivot + 1; clear <= endI; clear++)
		{
			LinkedList<Integer> vi = grid[clear];
			vi.remove(value);
		}
		
		// remove it from every cell in the column
		for (int clear = pivot + SudokuConstants.PUZZLE_WIDTH; clear < grid.length; clear += SudokuConstants.PUZZLE_WIDTH)
		{
			LinkedList<Integer> vi = grid[clear];
			vi.remove(value);
		}
		
		// remove it from every cell in the quadrant
		int x = pivot % SudokuConstants.PUZZLE_WIDTH;
		int y = pivot / SudokuConstants.PUZZLE_WIDTH;
		int gridStartX = (x / SudokuConstants.GRID_WIDTH) * SudokuConstants.GRID_WIDTH;
		int gridStartY = (y / SudokuConstants.GRID_WIDTH) * SudokuConstants.GRID_WIDTH;
		int gridEndX = gridStartX + SudokuConstants.GRID_WIDTH - 1;
		int gridEndY = gridStartY + SudokuConstants.GRID_WIDTH - 1;
		for (int gy = gridStartY; gy <= gridEndY; gy++)
			for (int gx = gridStartX; gx <= gridEndX; gx++)
			{
				if ((gy != y) && (gx != x))
				{
					LinkedList<Integer> vi = grid[gx + (gy * SudokuConstants.PUZZLE_WIDTH)];
					vi.remove(value);
				}
			}
	}

	public GameGrid getResultingGrid()
	{
		int [] values = new int [SudokuConstants.PUZZLE_BLOCK_COUNT];
		for (int i=0; i<SudokuConstants.PUZZLE_BLOCK_COUNT; i++)
		{
			values[i] = SudokuConstants.VALUE_EMPTY;
			
			LinkedList<Integer> pos = grid[i];
			if (pos.size() > 1)
			{
				String msg = "Unable to translate PuzzleCreationGameGrid to GameGrid because puzzle isn't completed!  Pos=" + i + " Size=" + pos.size();
				log.error(msg);
				throw new RuntimeException(msg);
			}
			else if (pos.size() == 1)
				values[i] = pos.get(0);
		}
		
		return new GameGrid(values);
	}

	public boolean isFullyPopulated()
	{
		for (int i=0; i<SudokuConstants.PUZZLE_BLOCK_COUNT; i++)
		{
			LinkedList<Integer> moves = grid[i];
			if ((moves == null) || (moves.size() != 1))
				return false;
		}
		
		return true;
	}
	
	public Object clone()
	{
		PuzzleCreationGameGrid freshCopy = new PuzzleCreationGameGrid();
		
		for (int i=0; i < freshCopy.grid.length; i++)
		{
			if (grid[i] != null)
				freshCopy.grid[i] = (LinkedList<Integer>)grid[i].clone();
		}
		
		return freshCopy;
	}
}
