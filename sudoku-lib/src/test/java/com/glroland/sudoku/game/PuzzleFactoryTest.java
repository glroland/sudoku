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
		Puzzle puzzle = pz.createPuzzle();
		assertNotNull(puzzle);
	}

}
