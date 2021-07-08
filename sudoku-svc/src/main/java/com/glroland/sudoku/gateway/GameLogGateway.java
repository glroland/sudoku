package com.glroland.sudoku.gateway;

import com.glroland.sudoku.Board;
import com.glroland.sudoku.Game;
import com.glroland.sudoku.util.SudokuConstants;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class GameLogGateway {
    
    private static final Log log = LogFactory.getLog(GameLogGateway.class);
    
    @Autowired
    private RestTemplate restTemplate;

    @Value("${gamelog.url}")
    private String gameLogServiceUrl;

    @Value("${gamelog.failOnError}")
    private boolean gameLogFailOnError = false;

    private void appendGameBoard(StringBuilder builder, Board board)
    {
        // validate parameters
        if ((builder == null) || (board == null))
        {
            String msg = "Input parameter builder or board is null";
            log.error(msg);
            throw new RuntimeException(msg);
        }

        // get and validate grid
        int [][] grid = board.getGrid();
        if (grid == null)
        {
            String msg = "When appending game board to JSON request, we found that the resulting board array within the object was null";
            log.error(msg);
            throw new RuntimeException(msg);
        }

        for (int y=0; y<SudokuConstants.PUZZLE_WIDTH; y++)
        {
            for (int x=0; x<SudokuConstants.PUZZLE_HEIGHT; x++)
            {
                if (grid[x][y] == 0)
                    builder.append(" ");
                else
                    builder.append(grid[x][y]);
            }
        }
    }

    public long logGame(Game game)
    {
        String url = gameLogServiceUrl + "/log/game";
        log.info("Game Log URL for logging Game: " + url);
        
        // build request
        StringBuilder requestJson = new StringBuilder();
        requestJson.append("{\"puzzle\": \"");
        appendGameBoard(requestJson, game.getPuzzle());
        requestJson.append("\",\"solution\": \"");
        appendGameBoard(requestJson, game.getSolution());
        requestJson.append("\"}");
        log.info("Game Log Request JSON: " + requestJson.toString());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<String>(requestJson.toString(), headers);

        try
        {
            String gameIdStr = restTemplate.postForObject(url, request, String.class);
            log.info("Logged Game ID = " + gameIdStr);
            return Long.parseLong(gameIdStr);
        }
        catch (RestClientException e)
        {
            String msg = "Caught exception while trying to log game!";
            if (gameLogFailOnError)
            {
                log.error(msg, e);
                throw e;
            }
            log.warn(msg, e);
        }

        return 0;
    }

    public long logGameMove(long gameId, int x, int y, int value)
    {
        String url = gameLogServiceUrl + "/log/move/" + gameId + "/" + x + "/" + y + "/" + value;
        log.info("Game Log URL for logging Game Move: " + url);

        try
        {
            String gameMoveIdStr = restTemplate.postForObject(url, null, String.class);
            log.info("Logged Game Move ID = " + gameMoveIdStr);
            return Long.parseLong(gameMoveIdStr);
        }
        catch (RestClientException e)
        {
            String msg = "Caught exception while trying to log game move!";
            if (gameLogFailOnError)
            {
                log.error(msg, e);
                throw e;
            }
            log.warn(msg, e);
        }

        return 0;
    }
}
