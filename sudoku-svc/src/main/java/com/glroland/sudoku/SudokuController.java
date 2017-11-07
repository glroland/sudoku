package com.glroland.sudoku;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.glroland.sudoku.game.Puzzle;
import com.glroland.sudoku.game.PuzzleFactory;
import com.glroland.sudoku.game.Solver;
import com.glroland.sudoku.model.GameGrid;
import com.glroland.sudoku.model.PlayableGameGrid;
import com.glroland.sudoku.util.SudokuConstants;

@RestController
public class SudokuController {

    @RequestMapping("/")
    public String index() {
        return "Sudoku!";
    }

    @CrossOrigin(origins = "*")
    @RequestMapping("/generate")
    public Game generate()
    {
    	PuzzleFactory factory = new PuzzleFactory();
    	Puzzle puzzle = factory.createPuzzle();
    	GameGrid initial = puzzle.getInitialPuzzle();
    	GameGrid solution = puzzle.getPuzzleSolution();
    	
    	Game game = new Game();
    	game.setPuzzle(gameGridToBoard(initial));
    	game.setSolution(gameGridToBoard(solution));
    	
    	return game;
    }
    
    @CrossOrigin(origins = "*")
    @RequestMapping("/solve")
    public Board solve(@RequestParam(value="puzzle") Board puzzle)
    {
    	GameGrid input = boardToGameGrid(puzzle);
    	GameGrid output = Solver.createSolution(input);
    	Board board = gameGridToBoard(output);
    	
    	return board;
    }
    
    private GameGrid boardToGameGrid(Board input)
    {
    	// validate arguments
    	if ((input == null) || (input.getGrid() == null))
    	{
    		throw new IllegalArgumentException("Null or empty puzzle board passed to service");
    	}
    	
    	// grab and validate board grid
    	int [][] grid = input.getGrid();
    	int width = grid.length;
    	if (width != SudokuConstants.PUZZLE_WIDTH)
    	{
    		throw new IllegalArgumentException("Puzzle board is expected to be of length " + SudokuConstants.PUZZLE_WIDTH + " but is actually of size " + width);
    	}
    	int height = grid[0].length;
    	if (height != SudokuConstants.PUZZLE_HEIGHT)
    	{
    		throw new IllegalArgumentException("Puzzle board is expected to be of length " + SudokuConstants.PUZZLE_HEIGHT + " but is actually of size " + height);
    	}    	
    			
    	// translate board to game grid
    	PlayableGameGrid output = new PlayableGameGrid();
    	for (int y=0; y<height; y++)
    	{
    		for (int x=0; x<width; x++)
    		{
    			int v = grid[x][y];
    			if ((v != SudokuConstants.VALUE_EMPTY) && (v < 1) && (x > SudokuConstants.PUZZLE_WIDTH))
    			{
    				throw new IllegalArgumentException("Puzzle board contains an invalid value at square (" + x + "," + y + "): " + v);
    			}
    			output.setValue(x, y, v);
    		}
    	}
    	
    	return output;
    }
    
    private Board gameGridToBoard(GameGrid input)
    {
    	// validate arguments
    	if (input == null)
    	{
    		throw new IllegalArgumentException("Null or empty game grid passed to conversion utility");
    	}
    	
    	// grab and validate board grid
    	if ((input.getWidth() != SudokuConstants.PUZZLE_WIDTH) || (input.getHeight() != SudokuConstants.PUZZLE_HEIGHT))
    	{
    		throw new IllegalArgumentException("Game grid is not of the correct dimensions: " + input.getWidth() + "x" + input.getHeight());
    	}
    	    			
    	// translate board to game grid
    	Board output = new Board();
    	int [][] grid = new int[SudokuConstants.PUZZLE_HEIGHT][SudokuConstants.PUZZLE_HEIGHT];
    	output.setGrid(grid);
    	for (int y=0; y<SudokuConstants.PUZZLE_HEIGHT; y++)
    	{
    		for (int x=0; x<SudokuConstants.PUZZLE_HEIGHT; x++)
    		{
    			int v = input.getValue(x, y);
    			if ((v != SudokuConstants.VALUE_EMPTY) && (v < 1) && (x > SudokuConstants.PUZZLE_WIDTH))
    			{
    				throw new IllegalArgumentException("Game grid contains an invalid value at square (" + x + "," + y + "): " + v);
    			}
    			grid[x][y] = v;
    		}
    	}
    	
    	return output;
    }
}
