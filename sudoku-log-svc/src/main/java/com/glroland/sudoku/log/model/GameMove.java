package com.glroland.sudoku.log.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.glroland.sudoku.log.util.Constants;

@Entity
@Table(name="GAME_MOVE")
public class GameMove {
        
    @Id
    @GeneratedValue
    @Column(name="GAME_MOVE_ID")
    private long id;
    
    @ManyToOne
    @JoinColumn(name="GAME_ID", nullable=false)
    private Game game;

    @Column(name="X")
    @NotNull
    @Min(0)
    @Max(Constants.BOARD_WIDTH - 1)
    private int x;

    @Column(name="Y")
    @NotNull
    @Min(0)
    @Max(Constants.BOARD_HEIGHT - 1)
    private int y;

    @Column(name="VALUE")
    @NotNull
    private int value;

    @Column(name="CREATE_DATE")
    @NotNull
    private Date createDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

}
