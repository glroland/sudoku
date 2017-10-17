package com.glroland.sudoku.game;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

import com.glroland.sudoku.model.GameGrid;
import com.glroland.sudoku.util.SudokuConstants;

public class PuzzleCreationTreeNode 
{
	private LinkedList<PuzzleCreationTreeNode> nextNodes = new LinkedList<PuzzleCreationTreeNode>();
	private PuzzleCreationTreeNode parentNode = null;
	
	private final PuzzleCreationGameGrid incomingGrid;
	private int myMovePosition = -1;
	private PuzzleCreationGameGrid outgoingGrid;
	
	private Random r = new Random();

	private static int c = 0;
	
	public PuzzleCreationTreeNode()
	{
		this(null, 0);
	}

	public PuzzleCreationTreeNode(PuzzleCreationGameGrid in, int position)
	{
		if (in == null)
		{
			incomingGrid = new PuzzleCreationGameGrid();
			for (int i=0; i<SudokuConstants.PUZZLE_BLOCK_COUNT; i++)
				incomingGrid.getMoveList(i).addAll(SudokuConstants.ALL_VALUES);
		}
		else
		{
			incomingGrid = in;
		}
		myMovePosition = position;
		
		synchronized(PuzzleCreationTreeNode.class) {
			c++;
			System.out.println("Node instance count: " + c);
		}
	}
	
	public void populate()
	{
		nextNodes.clear();
		
		// select a value in the current square
		LinkedList<Integer> moves = (LinkedList<Integer>)incomingGrid.getMoveList(myMovePosition).clone();
		Collections.shuffle(moves, r);

		for (Integer value : moves)
		{
			PuzzleCreationGameGrid nextGrid = (PuzzleCreationGameGrid)incomingGrid.clone();
			nextGrid.getMoveList(myMovePosition).clear();
			nextGrid.getMoveList(myMovePosition).add(value);
			nextGrid.cleanseValue(value,  myMovePosition);
			outgoingGrid = nextGrid;

			if (myMovePosition < (SudokuConstants.PUZZLE_BLOCK_COUNT - 1))
			{
				PuzzleCreationTreeNode node = new PuzzleCreationTreeNode(nextGrid, myMovePosition+1);
				node.parentNode = this;
				nextNodes.add(node);
				node.populate();
				outgoingGrid = node.getInputGrid();
			}
			if (outgoingGrid.isFullyPopulated())
				return;
		}
	}
	
	public PuzzleCreationGameGrid getInputGrid()
	{
		return incomingGrid;
	}
	
	public GameGrid getResult()
	{
		if (outgoingGrid == null)
			return null;
		
		return outgoingGrid.getResultingGrid();
	}
}
