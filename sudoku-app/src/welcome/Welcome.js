import React, {Component} from "react";
import {
  withRouter
} from 'react-router-dom';
import "../App.css";

class Welcome extends Component {
    constructor(props) {
      super(props);
      this.state = {
      };
  
    }
  
    render() {
      return (
        <div className="welcome">
          <h1 className="welcome-title">Sudoku!</h1>
          <br/>
          <br/>
          <button className="welcome-button" onClick={() => this.newGame()}>New Game</button><br />
          <br/>
          <button className="welcome-button" onClick={() => this.scanGame()}>Scan Board</button><br />
          <br/>
          <button className="welcome-button" onClick={() => this.options()}>Options</button><br />
          <br/>
          <br/>
          <div className="welcome-text">Copyright 2019 Lee Roland</div>
        </div>);
    }
  
    newGame() {
      this.props.history.push('/game');
    }

    scanGame() {
      this.props.history.push('/scan');
    }

    options() {
      this.props.history.push('/options');
    }
  }
  
  export default withRouter(Welcome);
  