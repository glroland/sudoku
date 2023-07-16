package com.glroland.sudoku;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glroland.sudoku.game.Puzzle;
import com.glroland.sudoku.game.PuzzleFactory;
import com.glroland.sudoku.game.Solver;
import com.glroland.sudoku.gateway.GameLogGateway;
import com.glroland.sudoku.model.GameGrid;
import com.glroland.sudoku.model.PlayableGameGrid;
import com.glroland.sudoku.util.SudokuConstants;

@RestController
public class SudokuController {

	private static final Log log = LogFactory.getLog(SudokuController.class);

	@Autowired
	private GameLogGateway gameLogGateway;

    @RequestMapping("/")
    public String index() {
        return "Sudoku Rules!";
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/generateBulkCsv", produces = "text/plain" /* "text/csv" */)
    public String generateBulkCsv(@RequestParam(defaultValue = "1000") long quantity)
    {
		PuzzleFactory factory = new PuzzleFactory();

		StringBuilder sb = new StringBuilder();
		for (long i=0; i<quantity; i++)
		{
			Puzzle puzzle = factory.createPuzzle();
			GameGrid initial = puzzle.getInitialPuzzle();
			GameGrid solution = puzzle.getPuzzleSolution();

			initial.appendBoardString(sb);
			sb.append(",");
			solution.appendBoardString(sb);
			sb.append("\n");
		}

		return sb.toString();
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
    	
		gameLogGateway.logGame(game);
		
		if (log.isInfoEnabled())
		{
			log.info("Game Generated with ID #" + game.getGameId());
		}

    	return game;
    }
    
    @CrossOrigin(origins = "*")
    @RequestMapping("/logMove")
    public long logMove(@RequestParam(value="gameId") long gameId, @RequestParam(value="x") int x, @RequestParam(value="y") int y, @RequestParam(value="value") int value)
    {
		if (log.isInfoEnabled())
		{
			log.info("Logging Move for Game ID# " + gameId + " - (" + x + ", " + y + ") " + value);

		}
		return gameLogGateway.logGameMove(gameId, x, y, value);
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
    		String msg = "Null or empty puzzle board passed to service";
			log.error(msg);
    		throw new IllegalArgumentException(msg);
    	}
    	
    	// grab and validate board grid
    	int [][] grid = input.getGrid();
    	int width = grid.length;
    	if (width != SudokuConstants.PUZZLE_WIDTH)
    	{
    		String msg = "Puzzle board is expected to be of length " + SudokuConstants.PUZZLE_WIDTH + " but is actually of size " + width;
			log.error(msg);
    		throw new IllegalArgumentException(msg);
    	}
    	int height = grid[0].length;
    	if (height != SudokuConstants.PUZZLE_HEIGHT)
    	{
    		String msg = "Puzzle board is expected to be of length " + SudokuConstants.PUZZLE_HEIGHT + " but is actually of size " + height;
			log.error(msg);
    		throw new IllegalArgumentException(msg);
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
    				String msg = "Puzzle board contains an invalid value at square (" + x + "," + y + "): " + v;
					log.error(msg);
    				throw new IllegalArgumentException(msg);
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
    		String msg = "Null or empty game grid passed to conversion utility";
			log.error(msg);
    		throw new IllegalArgumentException(msg);
    	}
    	
    	// grab and validate board grid
    	if ((input.getWidth() != SudokuConstants.PUZZLE_WIDTH) || (input.getHeight() != SudokuConstants.PUZZLE_HEIGHT))
    	{
    		String msg = "Game grid is not of the correct dimensions: " + input.getWidth() + "x" + input.getHeight();
			log.error(msg);
    		throw new IllegalArgumentException(msg);
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
    				String msg = "Game grid contains an invalid value at square (" + x + "," + y + "): " + v;
					log.error(msg);
    				throw new IllegalArgumentException(msg);
    			}
    			grid[x][y] = v;
    		}
    	}
    	
    	return output;
    }
}
