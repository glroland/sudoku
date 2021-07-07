package com.glroland.sudoku.log;

public class LogGameRequest {
    private String solution;
    private String puzzle;

    public String getSolution() {
        return solution;
    }
    public void setSolution(String solution) {
        this.solution = solution;
    }
    public String getPuzzle() {
        return puzzle;
    }
    public void setPuzzle(String puzzle) {
        this.puzzle = puzzle;
    }
}
