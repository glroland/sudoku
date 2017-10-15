package com.glroland.sudoku.game;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.glroland.sudoku.model.GameGrid;
import com.glroland.sudoku.model.SampleGrids;
import com.glroland.sudoku.util.SudokuConstants;

public class PuzzleFactoryTest {

	@Before
	public void setUp() throws Exception {
	}
	
	@Test
	public void testSolveRecursionOnCompletePuzzle()
	{
		PuzzleCreationGameGrid createGrid = new PuzzleCreationGameGrid();
		for (int i=0; i<SudokuConstants.PUZZLE_BLOCK_COUNT; i++)
		{
			ArrayList<Integer> moves = createGrid.getMoveList(i);
			moves.add(SampleGrids.GAME1_SOLUTION.getValue(i % SudokuConstants.PUZZLE_WIDTH, i / SudokuConstants.PUZZLE_WIDTH));
		}
		PuzzleCreationTreeNode node = new PuzzleCreationTreeNode(createGrid, 0);
		node.populate();
		GameGrid grid = node.getResult();
		assertNotNull(grid);
		assertTrue(grid.isValidBoard());
		assertTrue(grid.isSolved());
	}

	@Test
	public void testCreatePuzzle() {
		PuzzleFactory pz = new PuzzleFactory();
//		Puzzle p = pz.createPuzzle();
//		assertNotNull(p);
//		assertTrue(p.getPuzzleSolution().isValidBoard());
//		assertTrue(p.getPuzzleSolution().isSolved());
	}
}
