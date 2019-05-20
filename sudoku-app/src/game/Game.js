import React, {Component} from "react";
import {
  withRouter
} from 'react-router-dom';
import "../App.css";
import Board from "./Board";
import GameValuesSelection from "./GameValuesSelection";

class Game extends Component {
    constructor(props) {
      super(props);
      this.state = {
      };
  
      this.gameValueSelectionRef = React.createRef();
      this.boardRef = React.createRef();
      this.gameStatusRef = React.createRef();
  
      this.newGame();
    }
  
    setStatus(msg) {
      if (this.gameStatusRef.current != null) {
        this.gameStatusRef.current.value = msg;
      }
    }
  
    newGame() {
      this.setStatus("");
      this.puzzleGrid = Array(9);
      for (var y=0; y<9; y++) {
        this.puzzleGrid[y] = Array(9).fill(0);
      }
      this.solutionGrid = JSON.parse(JSON.stringify(this.puzzleGrid));
      this.initialGrid = JSON.parse(JSON.stringify(this.puzzleGrid));
  
      console.log(process.env);
      var serviceUrl = process.env.REACT_APP_SUDOKU_URL_SVC || 'http://localhost:8080';
      console.log("Service URL = " + serviceUrl);
  
      process.env.NODE_TLS_REJECT_UNAUTHORIZED = "0";
      fetch(serviceUrl + '/generate')
      .then(results => {
        return results.json();
      }).then(data => {
        this.puzzleGrid = data.puzzle.grid;
        console.log("Puzzle = " + this.puzzleGrid);
        this.solutionGrid = data.solution.grid;
        console.log("Solution = " + this.solutionGrid);
        this.initialGrid = JSON.parse(JSON.stringify(this.puzzleGrid));
        console.log("Initial = " + this.initialGrid);
  
        this.forceUpdate();
      }).catch(error => {
        console.log("An error occurred while contacting server for new puzzle: " + error);
        this.setStatus("Unable to contact server!");
      })
    }
    
    resetGame() {
      this.setStatus("");
      this.puzzleGrid = JSON.parse(JSON.stringify(this.initialGrid));
      this.gameValueSelectionRef.current.reset();
      this.forceUpdate();
    }
  
    checkGame() {
      this.setStatus("");
  
      // compare populated squares to solution
      var x, y;
      for (y=0; y<9; y++) {
        for (x=0; x<9; x++) {
          if ((this.puzzleGrid[y][x] !== 0) && (this.puzzleGrid[y][x] !== this.solutionGrid[y][x])) {
            this.setStatus("Incorrect Value at (" + (x+1) + "," + (y+1) + ")");
            return;
          }
        }
      }
  
      // ensure its fully populated
      var isComplete = true;
      for (y=0; y<9; y++) {
        for (x=0; x<9; x++) {
          if (this.puzzleGrid[y][x] === 0) {
  //          this.setStatus(this.gameStatusRef.current.value = "Incomplete at (" + (x+1) + "," + (y+1) + ")");
  //          return;
            isComplete = false;
          }
        }
      }
  
      if (isComplete) {
        this.setStatus("Complete!");
      }
      else {
        this.setStatus("Accurate!");
      }
    }
  
    render() {
      return (<div>
        <header className="App-header">
          <h1 className="App-title">
            Sudoku!
          </h1>
        </header>
        <div className="App-body">
          <div className="game">
            <div className="game-board">
              <Board ref={this.boardRef} game={this}/><br/>
              <input ref={this.gameStatusRef} type="text" className="game-status"/><br/>
              <br/>
              <GameValuesSelection ref={this.gameValueSelectionRef} /><br/>
              <br/>
              <br/>
              <button className="game-button" onClick={() => this.resetGame()}>Reset</button>
              <button className="game-button" onClick={() => this.checkGame()}>Check</button>
              <button className="game-button" onClick={() => this.leaveGame()}>Leave</button>
            </div>
          </div>
        </div>
        </div>);
    }
  
    leaveGame() {
      this.props.history.push("/");
    }
    getMove() {
      return this.gameValueSelectionRef.current.getMove();
    }
  
    makeMove(x, y) {
      this.makeMoveWithValue(x, y, this.getMove());
    }
  
    makeMoveWithValue(x, y, val) {
      console.log("makeMove X=" + x + " Y=" + y + " Value=" + val);
      this.gameStatusRef.current.value = "";
  
      var isValid = true;
  
      // valid horizontal move?
      var ix;
      for (ix=0; ix<9; ix++) {
        if (x !== ix) {
          if (this.puzzleGrid[y][ix] === val) {
            isValid = false;
            break;
          }
        }
      }
  
      // valid vertical move?
      var iy;
      for (iy=0; iy<9; iy++) {
        if (y !== iy) {
          if (this.puzzleGrid[iy][x] === val) {
            isValid = false;
            break;
          }
        }
      }
  
      // valid grid move?
      var gridStartX = x - (x % 3);
      var gridStartY = y - (y % 3);
      for (iy=gridStartY; iy<gridStartY+3; iy++) {
        for (ix=gridStartX; ix<gridStartX+3; ix++) {
          if ((x !== ix) && (y !== iy)) {
            if (this.puzzleGrid[iy][ix] === val) {
              isValid = false;
              break;
            }
          }
        }
      }
  
      if(isValid) {
        this.puzzleGrid[y][x] = val;
        this.forceUpdate();
      }
      else {
        console.log("ILLEGAL MOVE: X=" + x + " Y=" + y +" V=" + val);
        this.setStatus("Illegal move attempted at (" + x + "," + y + ")");
      }
    }
  }
  
  export default withRouter(Game);
  