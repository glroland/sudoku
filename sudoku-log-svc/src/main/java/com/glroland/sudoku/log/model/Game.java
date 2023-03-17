package com.glroland.sudoku.log.model;

import java.util.Date;
import java.util.List;
import java.io.IOException;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import com.glroland.sudoku.log.util.Constants;

@Entity
@Table(name="GAME")
public class Game {
    
    @Id
    @GeneratedValue
    @Column(name="GAME_ID")
    private long id;

    @Column(name="PUZZLE")
    @NotNull
    @Size(min = Constants.BOARD_SQUARES, max = Constants.BOARD_SQUARES)
    private String startingPuzzle;

    @Column(name="SOLUTION")
    @NotNull
    @Size(min = Constants.BOARD_SQUARES, max = Constants.BOARD_SQUARES)
    private String solution;

    @OneToMany(mappedBy="game")
    private List<GameMove> moves;

    @Column(name="CREATE_DATE")
    @NotNull
    private Date createDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStartingPuzzle() {
        return startingPuzzle;
    }

    public void setStartingPuzzle(String startingPuzzle) {
        this.startingPuzzle = startingPuzzle;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() 
    {
        ObjectMapper mapper = new ObjectMapper();

        try 
        {
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            return mapper.writeValueAsString(this);
        } 
        catch (IOException e) 
        {
            throw new RuntimeException("Unable to serialize DTO to JSON", e);
        }
    }
}
