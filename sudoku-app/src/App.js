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

    fetch('http://svc-sudoku.apps.home.glroland.com:8080/generate')
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
    return (<div className="game">
      <div className="game-board">
        <Board ref={this.boardRef} game={this}/><br/>
        <br/>
        <input ref={this.gameStatusRef} type="text" className="game-status"/><br/>
        <br/>
        <GameValuesSelection ref={this.gameValueSelectionRef} /><br/>
        <br/>
        <button className="game-button" onClick={() => this.newGame()}>New</button>
        <button className="game-button" onClick={() => this.resetGame()}>Reset</button>
        <button className="game-button" onClick={() => this.checkGame()}>Check</button>
      </div>
    </div>);
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

class Board extends Component {
  constructor(props) {
    super(props);
    this.state = {
    };

    this.squareRefs = Array(81);
    for (var i = 0; i < this.squareRefs.length; i++)
      this.squareRefs[i] = React.createRef();

    this.game = props.game;
  }

  render() {
    return (<div>
      <div className="board-row">
        <div className="board-grid-a">
          <Square ref={this.squareRefs[0]} index="0" game={this.game} />
          <Square ref={this.squareRefs[1]} index="1" game={this.game} />
          <Square ref={this.squareRefs[2]} index="2" game={this.game} />
        </div>
        <div className="board-grid-b">
          <Square ref={this.squareRefs[3]} index="3" game={this.game} />
          <Square ref={this.squareRefs[4]} index="4" game={this.game} />
          <Square ref={this.squareRefs[5]} index="5" game={this.game} />
        </div>
        <div className="board-grid-a">
          <Square ref={this.squareRefs[6]} index="6" game={this.game} />
          <Square ref={this.squareRefs[7]} index="7" game={this.game} />
          <Square ref={this.squareRefs[8]} index="8" game={this.game} />
        </div>
      </div>
      <div className="board-row">
        <div className="board-grid-a">
          <Square ref={this.squareRefs[9]} index="9" game={this.game} />
          <Square ref={this.squareRefs[10]} index="10" game={this.game} />
          <Square ref={this.squareRefs[11]} index="11" game={this.game} />
        </div>
        <div className="board-grid-b">
          <Square ref={this.squareRefs[12]} index="12" game={this.game} />
          <Square ref={this.squareRefs[13]} index="13" game={this.game} />
          <Square ref={this.squareRefs[14]} index="14" game={this.game} />
        </div>
        <div className="board-grid-a">
          <Square ref={this.squareRefs[15]} index="15" game={this.game} />
          <Square ref={this.squareRefs[16]} index="16" game={this.game} />
          <Square ref={this.squareRefs[17]} index="17" game={this.game} />
        </div>
      </div>
      <div className="board-row">
        <div className="board-grid-a">
          <Square ref={this.squareRefs[18]} index="18" game={this.game} />
          <Square ref={this.squareRefs[19]} index="19" game={this.game} />
          <Square ref={this.squareRefs[20]} index="20" game={this.game} />
        </div>
        <div className="board-grid-b">
          <Square ref={this.squareRefs[21]} index="21" game={this.game} />
          <Square ref={this.squareRefs[22]} index="22" game={this.game} />
          <Square ref={this.squareRefs[23]} index="23" game={this.game} />
        </div>
        <div className="board-grid-a">
          <Square ref={this.squareRefs[24]} index="24" game={this.game} />
          <Square ref={this.squareRefs[25]} index="25" game={this.game} />
          <Square ref={this.squareRefs[26]} index="26" game={this.game} />
        </div>
      </div>

      <div className="board-row">
        <div className="board-grid-b">
          <Square ref={this.squareRefs[27]} index="27" game={this.game} />
          <Square ref={this.squareRefs[28]} index="28" game={this.game} />
          <Square ref={this.squareRefs[29]} index="29" game={this.game} />
        </div>
        <div className="board-grid-a">
          <Square ref={this.squareRefs[30]} index="30" game={this.game} />
          <Square ref={this.squareRefs[31]} index="31" game={this.game} />
          <Square ref={this.squareRefs[32]} index="32" game={this.game} />
        </div>
        <div className="board-grid-b">
          <Square ref={this.squareRefs[33]} index="33" game={this.game} />
          <Square ref={this.squareRefs[34]} index="34" game={this.game} />
          <Square ref={this.squareRefs[35]} index="35" game={this.game} />
        </div>
      </div>
      <div className="board-row">
        <div className="board-grid-b">
          <Square ref={this.squareRefs[36]} index="36" game={this.game} />
          <Square ref={this.squareRefs[37]} index="37" game={this.game} />
          <Square ref={this.squareRefs[38]} index="38" game={this.game} />
        </div>
        <div className="board-grid-a">
          <Square ref={this.squareRefs[39]} index="39" game={this.game} />
          <Square ref={this.squareRefs[40]} index="40" game={this.game} />
          <Square ref={this.squareRefs[41]} index="41" game={this.game} />
        </div>
        <div className="board-grid-b">
          <Square ref={this.squareRefs[42]} index="42" game={this.game} />
          <Square ref={this.squareRefs[43]} index="43" game={this.game} />
          <Square ref={this.squareRefs[44]} index="44" game={this.game} />
        </div>
      </div>
      <div className="board-row">
        <div className="board-grid-b">
          <Square ref={this.squareRefs[45]} index="45" game={this.game} />
          <Square ref={this.squareRefs[46]} index="46" game={this.game} />
          <Square ref={this.squareRefs[47]} index="47" game={this.game} />
        </div>
        <div className="board-grid-a">
          <Square ref={this.squareRefs[48]} index="48" game={this.game} />
          <Square ref={this.squareRefs[49]} index="49" game={this.game} />
          <Square ref={this.squareRefs[50]} index="50" game={this.game} />
        </div>
        <div className="board-grid-b">
          <Square ref={this.squareRefs[51]} index="51" game={this.game} />
          <Square ref={this.squareRefs[52]} index="52" game={this.game} />
          <Square ref={this.squareRefs[53]} index="53" game={this.game} />
        </div>
      </div>

      <div className="board-row">
        <div className="board-grid-a">
          <Square ref={this.squareRefs[54]} index="54" game={this.game} />
          <Square ref={this.squareRefs[55]} index="55" game={this.game} />
          <Square ref={this.squareRefs[56]} index="56" game={this.game} />
        </div>
        <div className="board-grid-b">
          <Square ref={this.squareRefs[57]} index="57" game={this.game} />
          <Square ref={this.squareRefs[58]} index="58" game={this.game} />
          <Square ref={this.squareRefs[59]} index="59" game={this.game} />
        </div>
        <div className="board-grid-a">
          <Square ref={this.squareRefs[60]} index="60" game={this.game} />
          <Square ref={this.squareRefs[61]} index="61" game={this.game} />
          <Square ref={this.squareRefs[62]} index="62" game={this.game} />
        </div>
      </div>
      <div className="board-row">
        <div className="board-grid-a">
          <Square ref={this.squareRefs[63]} index="63" game={this.game} />
          <Square ref={this.squareRefs[64]} index="64" game={this.game} />
          <Square ref={this.squareRefs[65]} index="65" game={this.game} />
        </div>
        <div className="board-grid-b">
          <Square ref={this.squareRefs[66]} index="66" game={this.game} />
          <Square ref={this.squareRefs[67]} index="67" game={this.game} />
          <Square ref={this.squareRefs[68]} index="68" game={this.game} />
        </div>
        <div className="board-grid-a">
          <Square ref={this.squareRefs[69]} index="69" game={this.game} />
          <Square ref={this.squareRefs[70]} index="70" game={this.game} />
          <Square ref={this.squareRefs[71]} index="71" game={this.game} />
        </div>
      </div>
      <div className="board-row">
        <div className="board-grid-a">
          <Square ref={this.squareRefs[72]} index="72" game={this.game} />
          <Square ref={this.squareRefs[73]} index="73" game={this.game} />
          <Square ref={this.squareRefs[74]} index="74" game={this.game} />
        </div>
        <div className="board-grid-b">
          <Square ref={this.squareRefs[75]} index="75" game={this.game} />
          <Square ref={this.squareRefs[76]} index="76" game={this.game} />
          <Square ref={this.squareRefs[77]} index="77" game={this.game} />
        </div>
        <div className="board-grid-a">
          <Square ref={this.squareRefs[78]} index="78" game={this.game} />
          <Square ref={this.squareRefs[79]} index="79" game={this.game} />
          <Square ref={this.squareRefs[80]} index="80" game={this.game} />
        </div>
      </div>
    </div>);
  }

  handleClick(square) {
    console.log("Board HandleClick  X=" + square.getX() + " Y=" + square.getY());
    this.game.makeMove(square.getX(), square.getY());
  }
}

class Square extends Component {
  constructor(props) {
    super(props);
    this.state = {
    };

    this.SQUARE_CLASS_NAME_REG = "square";
    this.SQUARE_CLASS_NAME_LOCKED = "squareReadOnly";

    this.game = props.game;
    this.index = props.index;
  }

  render() {
    var x = this.getX();
    var y = this.getY();

//    console.log("X=" + x + " Y=" + y + " GPG=" + this.game.puzzleGrid + " L=" + this.game.puzzleGrid.length + " L2=" + this.game.puzzleGrid[0].length);

    var value = this.game.puzzleGrid[y][x];
    if (value === 0) {
        value = "";
    }

    var isReadOnly = false;
    var cn = this.SQUARE_CLASS_NAME_REG;
    if (this.game.initialGrid[y][x] !== 0) {
      isReadOnly = true;
      cn = this.SQUARE_CLASS_NAME_LOCKED;
    }



//    console.log("Index=" + this.index + " Value=" + value + " DisplayValue=" + value + " R/O=" + isReadOnly + " ClassName=" + cn);

    if (isReadOnly) {
      return (<button className={cn}>
        {value}
      </button>);
    } else {
      return (<button className={cn} onClick={() => this.handleClick()}>
        {value}
      </button>);
    }
  }

  getX() {
    return this.index % 9;
  }

  getY() {
    return (this.index - (this.index % 9)) / 9;
  }

  handleClick() {
    console.log("Square Clicked - Game=" + this.game + " X=" + this.getX() + " Y=" + this.getY());
//    this.board.handleClick(this);
    this.game.makeMove(this.getX(), this.getY());
  }
}

class GameValuesSelection extends Component {
  constructor(props) {
    super(props);
    this.state = {
    };

    this.CLASS_NAME_REG = "playValueOption";
    this.CLASS_NAME_SEL = "playValueOptionSelected";

    this.gameValueRefs = Array(9);
    for (var i = 0; i < 9; i++)
      this.gameValueRefs[i] = React.createRef();
  }

  reset() {
    this.setSelection(0);
  }

  setSelection(value) {
    // reset selections
    for (var i = 0; i < 9; i++) {
      this.gameValueRefs[i].current.className = this.CLASS_NAME_REG;
    }

    // enable new selection
    this.gameValueRefs[value].current.className = this.CLASS_NAME_SEL;
    this.forceUpdate();
  }

  render() {
    return (<div>
      <button ref={this.gameValueRefs[0]} className={this.CLASS_NAME_SEL} onClick={() => this.onClick(0)}>1</button>
      <button ref={this.gameValueRefs[1]} className={this.CLASS_NAME_REG} onClick={() => this.onClick(1)}>2</button>
      <button ref={this.gameValueRefs[2]} className={this.CLASS_NAME_REG} onClick={() => this.onClick(2)}>3</button>
      <button ref={this.gameValueRefs[3]} className={this.CLASS_NAME_REG} onClick={() => this.onClick(3)}>4</button>
      <button ref={this.gameValueRefs[4]} className={this.CLASS_NAME_REG} onClick={() => this.onClick(4)}>5</button>
      <button ref={this.gameValueRefs[5]} className={this.CLASS_NAME_REG} onClick={() => this.onClick(5)}>6</button>
      <button ref={this.gameValueRefs[6]} className={this.CLASS_NAME_REG} onClick={() => this.onClick(6)}>7</button>
      <button ref={this.gameValueRefs[7]} className={this.CLASS_NAME_REG} onClick={() => this.onClick(7)}>8</button>
      <button ref={this.gameValueRefs[8]} className={this.CLASS_NAME_REG} onClick={() => this.onClick(8)}>9</button>
    </div>);
  }

  onClick(value) {
    this.setSelection(value);
  }

  getMove() {
    var move = 0;

    for (var i = 0; i < 9; i++) {
      if (this.gameValueRefs[i].current.className === this.CLASS_NAME_SEL)
        move = parseInt(this.gameValueRefs[i].current.innerText, 10);
    }

    console.log("GameValueSection Selected Move = " + move);
    return move;
  }
}

export default App;
