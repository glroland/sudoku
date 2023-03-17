package com.glroland.sudoku.log;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.glroland.sudoku.log.model.Game;
import com.glroland.sudoku.log.model.GameMove;
import com.glroland.sudoku.log.util.Constants;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/log")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.TEXT_PLAIN)
@ApplicationScoped
public class LogResource {

    private static final Logger log = LoggerFactory.getLogger(LogResource.class);

    @PersistenceContext
    EntityManager manager;
     
    @POST
    @Path("game")
    @Transactional
    public long game(LogGameRequest request) {
        // validate input parameters
        if (request == null) {
            String msg = "Service request parameter is null!";
            log.error(msg);
            throw new RuntimeException(msg);
        }
        if ((request.getPuzzle() == null) || (request.getPuzzle().length() == 0))
        {
            String msg = "Input parameter puzzle is empty!";
            log.error(msg);
            throw new RuntimeException(msg);
        }
        if ((request.getSolution() == null) || (request.getSolution().length() == 0))
        {
            String msg = "Input parameter puzzle is empty!";
            log.error(msg);
            throw new RuntimeException(msg);
        }

        // create new Game entity
        Game game = new Game();
        game.setCreateDate(new Date());
        game.setStartingPuzzle(request.getPuzzle());
        game.setSolution(request.getSolution());
        manager.persist(game);

        return game.getId();
    }

    @POST
    @Path("move/{gameId}/{x}/{y}/{value}")
    @Transactional
    public long move(@PathParam("gameId") long gameId, @PathParam("x") int x, @PathParam("y") int y, @PathParam("value") int value) {
        // validate input parameters
        if ((x < 0) || (x >= Constants.BOARD_WIDTH)) 
        {
            String msg = "Input parameter X is out of range.  Val=" + x;
            log.error(msg);
            throw new RuntimeException(msg);            
        }
        if ((y < 0) || (y >= Constants.BOARD_WIDTH)) 
        {
            String msg = "Input parameter Y is out of range.  Val=" + y;
            log.error(msg);
            throw new RuntimeException(msg);            
        }
        if ((value < 0) || (value > Constants.BOARD_WIDTH)) 
        {
            String msg = "Input parameter Value is out of range.  Val=" + value;
            log.error(msg);
            throw new RuntimeException(msg);            
        }

        // log game that the move is related to
        Game game = manager.find(Game.class, gameId);
        if (game == null)
        {
            String msg = "Unable to locate game associated with gameId.  GameID=" + gameId;
            log.error(msg);
            throw new RuntimeException(msg);
        }

        // create game move
        GameMove gameMove = new GameMove();
        gameMove.setGame(game);
        gameMove.setX(x);
        gameMove.setY(y);
        gameMove.setValue(value);
        gameMove.setCreateDate(new Date());
        manager.persist(gameMove);

        return gameMove.getId();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello RESTEasy";
    }
}