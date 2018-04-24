import React, {Component} from "react";
import "./App.css";

class App extends Component {
  render() {
    return (<div className="App">
      <header className="App-header">
        <h1 className="App-title">
          Sudoku
        </h1>{" "}
      </header>{" "}
      <div className="App-body">
        <Game/>
      </div>{" "}
    </div>);
  }
}

class Game extends Component {
  constructor(props) {
    super(props);
    this.state = {
      history: [
        {
          squares: Array(81).fill(null)
        }
      ],
      stepNumber: 0,
      xIsNext: true,
      puzzleGrid: null,
      solutionGrid: null,
      gameGrid: null
    };

    this.newGame();
  }

  newGame() {
    fetch('http://sudoku-svc-sudoku.apps.home.glroland.com/generate')
    .then(results => {
      return results.json();
    }).then(data => {
      this.state.puzzleGrid = data.puzzle.grid;
      this.state.solutionGrid = data.solution.grid;

      this.state.gameGrid = JSON.parse(JSON.stringify(data.puzzle.grid))
    })
  }

  render() {
    return (<div className="game">
      <div className="game-board">
        <Board game={this}/>
      </div>
    </div>);
  }

  handleClick(i) {
  }
}

class Board extends Component {
  render() {
    return (<div>
      <div className="board-row">
        <Square x="1" y="1" />
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
}

class Square extends Component {
  constructor(props) {
    super(props);
    this.state = {
      x: props.x,
      y: props.y,
      value: props.value,
      isLocked: false
    };

    if ((this.state.value >= 1) && (this.state.value <= 9)) {
      this.state.isLocked = true;
    }
  }

  render() {
    const classSquare = "square";

    var cn = classSquare;

    if (this.state.isLocked) {
      return (<button className={cn}>
        <b>{this.state.value}</b>
      </button>);
    } else {
      return (<button className={cn} onClick={i => this.handleClick()}>
        {this.state.value}
      </button>);
    }
  }

  handleClick() {
    console.log("clicked - " + this.state.pos + " board=" + this.state.board);
  }
}

export default App;
