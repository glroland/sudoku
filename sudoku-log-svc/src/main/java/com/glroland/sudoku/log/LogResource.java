package com.glroland.sudoku.log;

import java.util.Date;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.glroland.sudoku.log.model.Game;
import com.glroland.sudoku.log.model.GameMove;
import com.glroland.sudoku.log.util.Constants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

        // create game move
        Game game = manager.find(Game.class, gameId);
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