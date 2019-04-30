import React, {Component} from "react";
import {
  withRouter
} from 'react-router-dom';
import "../App.css";

class MarkStep extends Component {
    constructor(props) {
      super(props);
      this.state = {
      };
  
      this.canvasRef = React.createRef();
    }
  
    render() {
//        captureDiv.width = this.props.width;
//        captureDiv.height = this.props.width;
  
      return (
        <div className="camera">
          <div>Step #2: Mark Puzzle Grid</div>
          <canvas ref={this.canvasRef} width={this.props.width} height={this.props.height} />
          <button className="welcome-button" onClick={() => this.proceed()}>Proceed</button><br />
        </div>);
    }

    componentDidMount() {
        let hiddenImg = document.createElement("img");
        hiddenImg.src = this.props.imgData;

        let canvasDiv = this.canvasRef.current;
        let context = canvasDiv.getContext('2d');
        hiddenImg.onload = function() {
            context.drawImage(hiddenImg, 0, 0);
        }
    }

    proceed() {
        this.props.markCallback(this.props.markCallbackRef);
    }
  }
  
  export default withRouter(MarkStep);
  