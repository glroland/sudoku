import React, {Component} from "react";
import {
  withRouter
} from 'react-router-dom';
import "../App.css";

class ScanPage extends Component {
    constructor(props) {
      super(props);
      this.state = {
      };
  
    }
  
    render() {
      return (
        <div className="options">
          <h1 className="welcome-title">Sudoku!</h1>
          <br/>
          <br/>
          <button className="welcome-button" onClick={() => this.mainmenu()}>Back</button><br />
          <br/>
          <br/>
          <div className="welcome-text">Copyright 2019 Lee Roland</div>
        </div>);
    }
  
    mainmenu() {
      this.props.history.push('/');
    }
  }
  
  export default withRouter(ScanPage);
  