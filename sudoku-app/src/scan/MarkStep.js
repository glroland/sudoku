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
  
      this.canvasOnMouseDown = this.canvasOnMouseDown.bind(this);
      this.canvasOnMouseUp = this.canvasOnMouseUp.bind(this);
      this.canvasOnMouseMove = this.canvasOnMouseMove.bind(this);

      this.canvasRef = React.createRef();
      this.imageLoaded = false;
      this.mouseDown = false;

      this.leftX = 0;
      this.leftY = 0;
      this.rightX = this.props.width;
      this.rightY = this.props.height;

      this.hiddenImg = document.createElement("img");
    }
  
    render() {
      return (
        <div className="camera">
          <div>Step #2: Mark Puzzle Grid</div>
          <canvas ref={this.canvasRef} width={this.props.width} height={this.props.height} onMouseDown={this.canvasOnMouseDown}  onMouseUp={this.canvasOnMouseUp} onMouseMove={this.canvasOnMouseMove}/>
          <button className="welcome-button" onClick={() => this.proceed()}>Proceed</button><br />
        </div>);
    }

    renderCanvas() {
        let width = this.rightX - this.leftX + 1;
        let height = this.rightY - this.leftY + 1;

        let canvas = this.canvasRef.current;
  
        let context = canvas.getContext('2d');
        context.drawImage(this.hiddenImg, 0, 0);
        context.beginPath();
        let blockWidth = width / 9;
        let blockHeight = height / 9;
        var x, y;
        for (x = 0; x < 9; x++) {
          for (y = 0; y < 9; y++) {
            context.rect(this.leftX + (x * blockWidth), 
                         this.leftY + (y * blockHeight), 
                         blockWidth, 
                         blockHeight);
          }
        }
        context.strokeStyle = 'red';
        context.lineWidth = 1;
        context.stroke();  
      }
  
    componentDidMount() {
        let hiddenImg = document.createElement("img");
        this.hiddenImg = hiddenImg;
        hiddenImg.src = this.props.imgData;

        let canvas = this.canvasRef.current;
        let context = canvas.getContext('2d');
        hiddenImg.onload = function() {
            context.drawImage(hiddenImg, 0, 0);
        }

    }

    localizeCoordinate(event) {
        let canvas = this.canvasRef.current;

        let x = event.pageX - canvas.offsetLeft;
        let y = event.pageY - canvas.offsetTop;

        return { x : x, y : y };
    }
    
    whichQuadrant(x, y)  {
        let canvas = this.canvasRef.current;

        let leftFlag = false;
        if ((x / canvas.width) < 0.50) {
            leftFlag = true;
        }

        let topFlag = false;
        if ((y / canvas.height) < 0.50) {
            topFlag = true;
        }
        
        if (leftFlag && topFlag) {
            return 1;
        }
        else if (!leftFlag && topFlag) {
            return 2;
        }
        else if(!leftFlag && !topFlag) { 
            return 3;
        }
        else {
            return 4;
        }
    }

    updateGrid(event) {
        let pos = this.localizeCoordinate(event);
        let quadrant = this.whichQuadrant(pos.x, pos.y);

        if (quadrant === 1) {
            this.leftX = pos.x;
            this.leftY = pos.y;
        }
        else if (quadrant === 2) {
            this.rightX = pos.x;
            this.leftY = pos.y;
        }
        else if (quadrant === 3) {
            this.rightX = pos.x;
            this.rightY = pos.y;
        }
        else if (quadrant === 4) {
            this.leftX = pos.x;
            this.rightY = pos.y;
        }

        this.renderCanvas();
    }

    canvasOnMouseDown(event) {
        this.mouseDown = true;
        this.updateGrid(event);
    }

    canvasOnMouseUp(event) {
        this.mouseDown = false;
        this.updateGrid(event);
    }

    canvasOnMouseMove(event) {
        if (this.mouseDown) {
            this.updateGrid(event);
        }
    }

    proceed() {
        let width = this.rightX - this.leftX + 1;
        let height = this.rightY - this.leftY + 1;

        let canvas = this.canvasRef.current;  
        let context = canvas.getContext('2d');

        context.drawImage(this.hiddenImg, 
            this.leftX, this.leftY, width, height,
            0, 0, canvas.width, canvas.height);

        let dataUrl = canvas.toDataURL('image/png');
        this.props.markCallback(this.props.markCallbackRef, dataUrl);
    }
  }
  
  export default withRouter(MarkStep);
  