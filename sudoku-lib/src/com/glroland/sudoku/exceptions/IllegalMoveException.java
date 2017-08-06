package com.glroland.sudoku.exceptions;

public class IllegalMoveException extends RuntimeException {

	private static final long serialVersionUID = -4570565576407375389L;

	public IllegalMoveException(int x, int y, int v)
	{
		this("", x, y, v);
	}
	
	public IllegalMoveException(String msg, int x, int y, int v)
	{
		super("An attempt was made to make an illegal game move: X=" + x + " Y=" + y + " Val=" + v + "::" + msg);
	}
}
