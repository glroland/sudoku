package com.glroland.sudoku;

public class Game {
	private Board puzzle;
	private Board solution;
	private long gameId;
	
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
	public long getGameId() {
		return gameId;
	}
	public void setGameId(long gameId) {
		this.gameId = gameId;
	}
	
}
