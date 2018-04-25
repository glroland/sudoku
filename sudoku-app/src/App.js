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

      this.forceUpdate();
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
        <Square x="1" y="2" board={this} />
        <Square x="2" y="2" board={this} />
        <Square x="3" y="2" board={this} />
        <Square x="4" y="2" board={this} />
        <Square x="5" y="2" board={this} />
        <Square x="6" y="2" board={this} />
        <Square x="7" y="2" board={this} />
        <Square x="8" y="2" board={this} />
        <Square x="9" y="2" board={this} />
      </div>
      <div className="board-row">
        <Square x="1" y="3" board={this} />
        <Square x="2" y="3" board={this} />
        <Square x="3" y="3" board={this} />
        <Square x="4" y="3" board={this} />
        <Square x="5" y="3" board={this} />
        <Square x="6" y="3" board={this} />
        <Square x="7" y="3" board={this} />
        <Square x="8" y="3" board={this} />
        <Square x="9" y="3" board={this} />
      </div>
      <div className="board-row">
        <Square x="1" y="4" board={this} />
        <Square x="2" y="4" board={this} />
        <Square x="3" y="4" board={this} />
        <Square x="4" y="4" board={this} />
        <Square x="5" y="4" board={this} />
        <Square x="6" y="4" board={this} />
        <Square x="7" y="4" board={this} />
        <Square x="8" y="4" board={this} />
        <Square x="9" y="4" board={this} />
      </div>
      <div className="board-row">
        <Square x="1" y="5" board={this} />
        <Square x="2" y="5" board={this} />
        <Square x="3" y="5" board={this} />
        <Square x="4" y="5" board={this} />
        <Square x="5" y="5" board={this} />
        <Square x="6" y="5" board={this} />
        <Square x="7" y="5" board={this} />
        <Square x="8" y="5" board={this} />
        <Square x="9" y="5" board={this} />
      </div>
      <div className="board-row">
        <Square x="1" y="6" board={this} />
        <Square x="2" y="6" board={this} />
        <Square x="3" y="6" board={this} />
        <Square x="4" y="6" board={this} />
        <Square x="5" y="6" board={this} />
        <Square x="6" y="6" board={this} />
        <Square x="7" y="6" board={this} />
        <Square x="8" y="6" board={this} />
        <Square x="9" y="6" board={this} />
      </div>
      <div className="board-row">
        <Square x="1" y="7" board={this} />
        <Square x="2" y="7" board={this} />
        <Square x="3" y="7" board={this} />
        <Square x="4" y="7" board={this} />
        <Square x="5" y="7" board={this} />
        <Square x="6" y="7" board={this} />
        <Square x="7" y="7" board={this} />
        <Square x="8" y="7" board={this} />
        <Square x="9" y="7" board={this} />
      </div>
      <div className="board-row">
        <Square x="1" y="8" board={this} />
        <Square x="2" y="8" board={this} />
        <Square x="3" y="8" board={this} />
        <Square x="4" y="8" board={this} />
        <Square x="5" y="8" board={this} />
        <Square x="6" y="8" board={this} />
        <Square x="7" y="8" board={this} />
        <Square x="8" y="8" board={this} />
        <Square x="9" y="8" board={this} />
      </div>
      <div className="board-row">
        <Square x="1" y="9" board={this} />
        <Square x="2" y="9" board={this} />
        <Square x="3" y="9" board={this} />
        <Square x="4" y="9" board={this} />
        <Square x="5" y="9" board={this} />
        <Square x="6" y="9" board={this} />
        <Square x="7" y="9" board={this} />
        <Square x="8" y="9" board={this} />
        <Square x="9" y="9" board={this} />
      </div>
    </div>);
  }

  getValue(x, y) {
      return this.game.getPuzzle()[(y-1)][x-1];
  }

  handleClick(square) {
    console.log("HandleClick = " + square);
  }
}

class Square extends Component {
  constructor(props) {
    super(props);
    this.state = {
    };

    this.x = parseInt(props.x);
    this.y = parseInt(props.y);

    this.board = props.board;
  }

  render() {
    const classSquare = "square";

    var cn = classSquare;

    var value = this.getValue();
    let isLocked = true;
    if (value == 0) {
        isLocked = false;
        value = "";
    }

    if (isLocked) {
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
