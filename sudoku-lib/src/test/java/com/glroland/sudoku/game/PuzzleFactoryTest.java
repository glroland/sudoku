package com.glroland.sudoku.game;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

public class PuzzleFactoryTest {

	@Rule
    public Timeout globalTimeout = Timeout.seconds(5);

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
		System.out.println("Generated Puzzle:\n" + p.getPuzzleSolution().toString());
	}
}
