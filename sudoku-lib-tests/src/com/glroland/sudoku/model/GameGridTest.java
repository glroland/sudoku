package com.glroland.sudoku.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.glroland.sudoku.util.SudokuConstants;

public class GameGridTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testIsSolvedNo() {
		GameGrid grid = SampleGrids.GAME1;
		assertFalse(grid.isSolved());
	}

	@Test
	public void testIsValidLocationLarge() {
		GameGrid grid = SampleGrids.GAME1;
		assertFalse(grid.isValidLocation(SudokuConstants.PUZZLE_WIDTH, SudokuConstants.PUZZLE_HEIGHT));
	}

	@Test
	public void testIsValidLocationYes() {
		GameGrid grid = SampleGrids.GAME1;
		assertTrue(grid.isValidLocation(SudokuConstants.PUZZLE_WIDTH-1, SudokuConstants.PUZZLE_HEIGHT-1));
	}

	@Test
	public void testIsValidLocationYes2() {
		GameGrid grid = SampleGrids.GAME1;
		assertTrue(grid.isValidLocation(0, 0));
	}

	@Test
	public void testIsValidLocationNegative() {
		GameGrid grid = SampleGrids.GAME1;
		assertFalse(grid.isValidLocation(-1, -1));
	}
	@Test
	public void testIsValidMoveDupX() {
		GameGrid grid = SampleGrids.GAME1;
		assertFalse(grid.isValidMove(1, 0, 5));
	}

	@Test
	public void testIsValidMoveDupY() {
		GameGrid grid = SampleGrids.GAME1;
		assertFalse(grid.isValidMove(1, 6, 5));
	}

	@Test
	public void testIsValidMoveDupQ() {
		GameGrid grid = SampleGrids.GAME1;
		assertFalse(grid.isValidMove(5, 6, 5));
	}

	@Test
	public void testIsValidMoveYes() {
		GameGrid grid = SampleGrids.GAME1;
		assertTrue(grid.isValidMove(6, 6, 5));
	}

	@Test
	public void testIsValidMoveRedo() {
		GameGrid grid = SampleGrids.GAME1;
		assertTrue(grid.isValidMove(4, 7, 6));
	}

	@Test
	public void testIsValidMoveChangeValid() {
		GameGrid grid = SampleGrids.GAME1;
		assertTrue(grid.isValidMove(4, 7, 2));
	}

	@Test
	public void testIsValidMoveChangeInvalid() {
		GameGrid grid = SampleGrids.GAME1;
		assertFalse(grid.isValidMove(4, 7, 1));
	}

	@Test
	public void testIsBoardValidEmpty() {
		GameGrid grid = SampleGrids.EMPTY;
		assertTrue(grid.isValidBoard());
	}

	@Test
	public void testIsBoardValidYes() {
		GameGrid grid = SampleGrids.GAME1;
		assertTrue(grid.isValidBoard());
	}

	@Test
	public void testIsBoardValidNo() {
		GameGrid grid = SampleGrids.BAD_NOTVALID;
		assertFalse(grid.isValidBoard());
	}
}
