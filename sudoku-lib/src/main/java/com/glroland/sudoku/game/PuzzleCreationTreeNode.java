package com.glroland.sudoku.game;

import java.util.ArrayList;
import java.util.Random;

import com.glroland.sudoku.model.GameGrid;
import com.glroland.sudoku.util.SudokuConstants;

public class PuzzleCreationTreeNode 
{
	private ArrayList<PuzzleCreationTreeNode> nextNodes = new ArrayList<PuzzleCreationTreeNode>();
	private PuzzleCreationTreeNode parentNode = null;
	
	private final PuzzleCreationGameGrid incomingGrid;
	private int myMovePosition = -1;
	private PuzzleCreationGameGrid outgoingGrid;
	
	private Random r = new Random();

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
	}
	
	public void populate()
	{
		// select a value in the current square
		ArrayList<Integer> moves = (ArrayList<Integer>)incomingGrid.getMoveList(myMovePosition).clone();
		int moveCount;
		while ((moveCount = moves.size()) > 0)
		{
			moveCount = moves.size();		
			int select = r.nextInt(moveCount);
			int value = moves.remove(select);
			
			PuzzleCreationGameGrid nextGrid = (PuzzleCreationGameGrid)incomingGrid.clone();
			nextGrid.getMoveList(myMovePosition).clear();
			nextGrid.getMoveList(myMovePosition).add(value);
			nextGrid.cleanseValue(value,  myMovePosition);
			
			if (myMovePosition < (SudokuConstants.PUZZLE_BLOCK_COUNT - 1))
			{
				PuzzleCreationTreeNode next = new PuzzleCreationTreeNode(nextGrid, myMovePosition+1);
				next.parentNode = this;
				nextNodes.add(next);
			}
		}
			
		for (PuzzleCreationTreeNode node : nextNodes)
		{
			node.populate();
			outgoingGrid = node.getInputGrid();
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
