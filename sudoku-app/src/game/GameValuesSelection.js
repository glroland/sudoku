import React, {Component} from "react";
import "../App.css";

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
  
  export default GameValuesSelection;
  