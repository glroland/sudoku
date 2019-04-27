import React, {Component} from "react";
import "../App.css";
import Square from "./Square";

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
  
  export default Board;
  