package com.glroland.sudoku.exceptions;

public class InvalidValueException extends RuntimeException {
	private static final long serialVersionUID = -1676282135971835888L;

	public InvalidValueException(int v)
	{
		this(v, "");
	}

	public InvalidValueException(int v, String msg)
	{
		super("Illegal Value specified (" + v + "): " + msg);
	}
}
