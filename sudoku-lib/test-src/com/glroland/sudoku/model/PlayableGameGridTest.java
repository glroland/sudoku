package com.glroland.sudoku.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.glroland.sudoku.util.SudokuConstants;

public class PlayableGameGridTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testIsValidValueEmpty() {
		PlayableGameGrid grid = SampleGrids.PGAME1;
		assertTrue(grid.isValidValue(SudokuConstants.VALUE_EMPTY));
	}

	@Test
	public void testIsValidValueYes1() {
		PlayableGameGrid grid = SampleGrids.PGAME1;
		assertTrue(grid.isValidValue(SudokuConstants.VALUE_MIN));
	}

	@Test
	public void testIsValidValueYes2() {
		PlayableGameGrid grid = SampleGrids.PGAME1;
		assertTrue(grid.isValidValue(SudokuConstants.VALUE_MAX));
	}

	@Test
	public void testIsValidValueTooBig() {
		PlayableGameGrid grid = SampleGrids.PGAME1;
		assertFalse(grid.isValidValue(SudokuConstants.VALUE_MAX+1));
	}

	@Test
	public void testIsValidValueNegative() {
		PlayableGameGrid grid = SampleGrids.PGAME1;
		assertFalse(grid.isValidValue(-1));
	}


}
