import React, {Component} from "react";
import "./App.css";

class App extends Component {
  render() {
    return (<div className="App">
      <header className="App-header">
        <h1 className="App-title">
          Sudoku
        </h1>
      </header>
      <div className="App-body">
        <Game/>
      </div>
    </div>);
  }
}

class Game extends Component {
  constructor(props) {
    super(props);
    this.state = {
    };

    this.puzzleGrid = Array(81).fill(0);
    this.solutionGrid = Array(81).fill(0);
    this.newGame();
  }

  newGame() {
    fetch('http://sudoku-svc-sudoku.apps.home.glroland.com/generate')
    .then(results => {
      return results.json();
    }).then(data => {
      this.puzzleGrid = data.puzzle.grid;
      console.log("Puzzle = " + this.puzzleGrid);
      this.solutionGrid = data.solution.grid;
      console.log("Solution = " + this.solutionGrid);
    })
  }

  render() {
    return (<div className="game">
      <div className="game-board">
        <Board game={this}/>
      </div>
    </div>);
  }

  getPuzzle() {
    return this.puzzleGrid;
  }
}

class Board extends Component {
  constructor(props) {
    super(props);
    this.state = {
    };

    this.game = props.game;
  }

  render() {
    return (<div>
      <div className="board-row">
        <Square x="1" y="1" board={this} />
        <Square x="2" y="1" board={this} />
        <Square x="3" y="1" board={this} />
        <Square x="4" y="1" board={this} />
        <Square x="5" y="1" board={this} />
        <Square x="6" y="1" board={this} />
        <Square x="7" y="1" board={this} />
        <Square x="8" y="1" board={this} />
        <Square x="9" y="1" board={this} />
      </div>
      <div className="board-row">
      </div>
      <div className="board-row">
      </div>
      <div className="board-row">
      </div>
      <div className="board-row">
      </div>
      <div className="board-row">
      </div>
      <div className="board-row">
      </div>
      <div className="board-row">
      </div>
      <div className="board-row">
      </div>
    </div>);
  }

  getValue(x, y) {
      let pos = ((y - 1) * 9) + x - 1;
      return this.game.getPuzzle()[pos];
  }

  handleClick(square) {
    console.log("HandleClick = " + square);
  }
}

class Square extends Component {
  constructor(props) {
    super(props);
    this.state = {
      isLocked: false
    };

    this.x = parseInt(props.x);
    this.y = parseInt(props.y);

    this.board = props.board;
  }

  render() {
    const classSquare = "square";

    var cn = classSquare;

    var value = this.getValue();
    console.log("V = " + value);
    if (value == 0)
      value = "";

    if (this.state.isLocked) {
      return (<button className={cn}>
        <b>{value}</b>
      </button>);
    } else {
      return (<button className={cn} onClick={() => this.handleClick(this)}>
        {value}
      </button>);
    }
  }

  getValue() {
      return this.board.getValue(this.x, this.y);
  }

  handleClick(square) {
    console.log("Square Clicked - Square=" + square + " Board=" + this.board + " X=" + this.x + " Y=" + this.y + " Value=" + this.getValue() + " IsLocked=" + this.state.isLocked);
  }
}

export default App;
