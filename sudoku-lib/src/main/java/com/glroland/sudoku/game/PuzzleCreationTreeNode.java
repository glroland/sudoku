package com.glroland.sudoku.game;

import java.util.ArrayList;

public class PuzzleCreationTreeNode 
{
	private ArrayList<PuzzleCreationTreeNode> nextNodes = new ArrayList<PuzzleCreationTreeNode>();
	private PuzzleCreationTreeNode parentNode;
	
	private PuzzleCreationGameGrid incomingGrid;
	private int myMovePosition = -1;
	private int myMoveValue = -1;
	private PuzzleCreationGameGrid outgoingGrid;
	
	
}
