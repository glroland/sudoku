import React, {Component} from "react";
import "../App.css";

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
  
      var value = this.game.puzzleGrid[x][y];
      if (value === 0) {
          value = "";
      }
  
      var isReadOnly = false;
      var cn = this.SQUARE_CLASS_NAME_REG;
      if (this.game.initialGrid[x][y] !== 0) {
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
  
  export default Square;
  