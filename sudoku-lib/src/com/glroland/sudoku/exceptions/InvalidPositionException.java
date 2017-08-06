package com.glroland.sudoku.exceptions;

public class InvalidPositionException extends RuntimeException {
	private static final long serialVersionUID = -1676282135971835888L;

	public InvalidPositionException(int x, int y)
	{
		this(x, y, "");
	}

	public InvalidPositionException(int x, int y, String msg)
	{
		super("Illegal Position specified (" + x + ", " + y + "): " + msg);
	}
}
