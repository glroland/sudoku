import React, {Component} from "react";
import { Switch, Route } from 'react-router-dom';
import "./App.css";
import Game from "./game/Game";
import Welcome from "./welcome/Welcome";
import OptionsPage from "./options/OptionsPage";
import ScanPage from "./scan/ScanPage";

class App extends Component {
  render() {
    return (
      <div className="App">
        <Switch>
          <Route exact path='/' component={ Welcome }/>
          <Route path='/game' component={ Game }/>
          <Route path='/options' component={ OptionsPage }/>
          <Route path='/scan' component={ ScanPage }/>
        </Switch>
      </div>
    );
  }
}

export default App;
