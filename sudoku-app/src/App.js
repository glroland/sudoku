import React, {Component} from "react";
import { Routes, Route } from "react-router-dom";
import "./App.css";
import Game from "./game/Game";
import Welcome from "./welcome/Welcome";
import OptionsPage from "./options/OptionsPage";
import ScanPage from "./scan/ScanPage";

class App extends Component {
  render() {
    return (
      <div className="App">
        <Routes>
          <Route path='/' element={<Welcome />}/>
          <Route path='/game' element={<Game />}/>
          <Route path='/options' element={<OptionsPage />}/>
          <Route path='/scan' element={<ScanPage />}/>
        </Routes>
      </div>
    );
  }
}

export default App;
