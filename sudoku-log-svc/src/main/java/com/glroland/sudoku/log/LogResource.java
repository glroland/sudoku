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
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.glroland.sudoku.log.model.Game;

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
    public long gameStarted(LogGameRequest request) {
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

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello RESTEasy";
    }
}