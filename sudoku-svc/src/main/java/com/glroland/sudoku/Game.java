package com.glroland.sudoku;

public class Game {
	private Board puzzle;
	private Board solution;
	
	public Board getPuzzle() {
		return puzzle;
	}
	public void setPuzzle(Board puzzle) {
		this.puzzle = puzzle;
	}
	public Board getSolution() {
		return solution;
	}
	public void setSolution(Board solution) {
		this.solution = solution;
	}
}
