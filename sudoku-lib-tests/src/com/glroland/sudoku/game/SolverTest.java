package com.glroland.sudoku.game;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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
		assertNotNull("Null grid returned from solver", grid);
		assertTrue("Grid has not been solved: " + grid, grid.isSolved());
		assertTrue("Grid solution does not match expected solution: " + grid, SampleGrids.GAME1_SOLUTION.isDerivative(grid));
		System.out.println("Generated Solution to GAME1: \n" + grid);
	}

	@Test
	public void testSolveGameNotSolvable() {
		try
		{
			GameGrid grid = Solver.createSolution(SampleGrids.BAD_UNSOLVABLE);
			fail("Unsolvable puzzle should have threw an exception");
		}
		catch(RuntimeException e)
		{
		}
	}

	@Test
	public void testGetValidValuesForSquare_1()
	{
		List<Integer> values = Solver.getValidValuesForSquare(SampleGrids.GAME1, 5, 2);
		values.sort(null);
		assertTrue("Values list should have of size 3: " + values.toString() + "\n\nPos X=5, Y=2\n\nPuzzle:\n" + SampleGrids.GAME1, values.size() == 3);
		assertTrue("Values list index 0 should have been a 5: " + values.toString(), values.get(0) == 5);
		assertTrue("Values list index 1 should have been a 6: " + values.toString(), values.get(1) == 6);
		assertTrue("Values list index 2 should have been a 8: " + values.toString(), values.get(2) == 8);
	}

	@Test
	public void testGetValidValuesForSquare_2()
	{
		List<Integer> values = Solver.getValidValuesForSquare(SampleGrids.GAME1, 8, 8);
		values.sort(null);
		assertTrue("Values list should have of size 3: " + values.toString() + "\n\nPos X=8, Y=8\n\nPuzzle:\n" + SampleGrids.GAME1, values.size() == 3);
		assertTrue("Values list index 0 should have been a 4: " + values.toString(), values.get(0) == 4);
		assertTrue("Values list index 1 should have been a 6: " + values.toString(), values.get(1) == 6);
		assertTrue("Values list index 2 should have been a 8: " + values.toString(), values.get(2) == 8);
	}

	@Test
	public void testGetValidValuesForSquare_NonEmpty()
	{
		List<Integer> values = Solver.getValidValuesForSquare(SampleGrids.GAME1, 0, 0);
		values.sort(null);
		assertTrue("Values list should have of size 0: " + values.toString() + "\n\nPos X=0, Y=0\n\nPuzzle:\n" + SampleGrids.GAME1, values.size() == 0);
	}
}
