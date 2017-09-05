package com.glroland.sudoku.game;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PuzzleFactoryTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testCreatePuzzle() {
		PuzzleFactory pz = new PuzzleFactory();
		Puzzle p = pz.createPuzzle();
		assertNotNull(p);
		assertTrue(p.getPuzzleSolution().isValidBoard());
		assertTrue(p.getPuzzleSolution().isSolved());
	}
}
