package com.glroland.sudoku.game;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.glroland.sudoku.model.GameGrid;
import com.glroland.sudoku.model.SampleGrids;

public class SolverTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testSolveGame1() {
		GameGrid grid = Solver.createSolution(SampleGrids.GAME1);
		assertNotNull(grid);
	}

	@Test
	public void testGetValidValuesForSquare_1()
	{
		List<Integer> values = Solver.getValidValuesForSquare(SampleGrids.GAME1, 5, 2);
		assertTrue("Values list should have of size 1: " + values.toString(), values.size() == 1);
		assertTrue("Values list index 0 should have been a 5: " + values.toString(), values.get(0) == 5);
	}
}
