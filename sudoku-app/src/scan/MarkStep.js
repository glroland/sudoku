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

      this.grid1 = {x: 0, y: 0};
      this.grid2 = {x: this.props.width, y: 0};
      this.grid3 = {x: this.props.width, y: this.props.height};
      this.grid4 = {x: 0, y: this.props.height};

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

    drawQuad(context, x1, y1, x2, y2, x3, y3, x4, y4) {
//        context.beginPath();
        context.moveTo(x1, y1);
        context.lineTo(x2, y2);
        context.lineTo(x3, y3);
        context.lineTo(x4, y4);
        context.lineTo(x1, y1);
//        context.stroke();
    }

    renderCanvas() {
        let width = this.grid2.x - this.grid1.x + 1;
        let height = this.grid3.y - this.grid2.y + 1;
        let blockWidth = width / 9;
        let blockHeight = height / 9;

        let canvas = this.canvasRef.current;
  
        let context = canvas.getContext('2d');
        context.drawImage(this.hiddenImg, 0, 0);
        context.beginPath();
/*        var x, y;
        for (x = 0; x < 9; x++) {
          for (y = 0; y < 9; y++) {
            let block1 = {x: this.grid1.x + (x * ((this.grid2.x - this.grid1.x + 1) / 9)), //yes
                          y: this.grid1.y + (y * ((this.grid2.y - this.grid1.y + 1) / 9))};
            let block2 = {x: this.grid2.x - ((8-x) * ((this.grid2.x - this.grid1.x + 1) / 9)), 
                          y: this.grid2.y - ((8-y) * ((this.grid2.y - this.grid1.y + 1) / 9))};
            let block3 = {x: this.grid3.x - ((8-x) * ((this.grid3.x - this.grid4.x + 1) / 9)), 
                          y: this.grid3.y - ((8-y) * ((this.grid3.y - this.grid4.y + 1) / 9))};
            let block4 = {x: this.grid4.x + (x * ((this.grid3.x - this.grid4.x + 1) / 9)), //yes
                          y: this.grid4.y + (y * ((this.grid3.y - this.grid4.y + 1) / 9))};
             
            if ((x === 0) && (y === 0)) {
                console.log("Logical (" + x + " " + y + ")");
                console.log("Grid1 (" + this.grid1.x + " " + this.grid1.y + ") " + 
                            "Grid2 (" + this.grid2.x + " " + this.grid2.y + ") " +
                            "Grid3 (" + this.grid3.x + " " + this.grid3.y + ") " +
                            "Grid4 (" + this.grid4.x + " " + this.grid4.y + ")");
                console.log("Block1 (" + block1.x + " " + block1.y + ") " + 
                            "Block2 (" + block2.x + " " + block2.y + ") " +
                            "Block3 (" + block3.x + " " + block3.y + ") " +
                            "Block4 (" + block4.x + " " + block4.y + ")");
            }

            this.drawQuad(context, 
                          block1.x, block1.y, 
                          block2.x, block2.y, 
                          block3.x, block3.y, 
                          block4.x, block4.y);
          }
        }*/

        this.drawQuad(context,
                      this.grid1.x, this.grid1.y,
                      this.grid2.x, this.grid2.y,
                      this.grid3.x, this.grid3.y,
                      this.grid4.x, this.grid4.y);
                      
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
            this.grid1.x = pos.x;
            this.grid1.y = pos.y;
        }
        else if (quadrant === 2) {
            this.grid2.x = pos.x;
            this.grid2.y = pos.y;
        }
        else if (quadrant === 3) {
            this.grid3.x = pos.x;
            this.grid3.y = pos.y;
        }
        else if (quadrant === 4) {
            this.grid4.x = pos.x;
            this.grid4.y = pos.y;
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
/*
        let horScaling = 0;
        let horSkewing = 0;
        let vertSkewing = 0;
        let vertScaling = 0;
        let horMoving = 0;
        let vertMoving = 0;

        let canvas = this.canvasRef.current;  
        let context = canvas.getContext('2d');
        context.transform(horScaling, horSkewing, vertSkewing, vertScaling, horMoving, vertMoving);
*/

        let width = this.grid2.x - this.grid1.x + 1;
        let height = this.grid3.y - this.grid1.y + 1;

        let canvas = this.canvasRef.current;  
        let context = canvas.getContext('2d');

        context.drawImage(this.hiddenImg, 
            this.grid1.x, this.grid1.y, width, height,
            0, 0, canvas.width, canvas.height);

        let dataUrl = canvas.toDataURL('image/png');
        this.props.markCallback(this.props.markCallbackRef, dataUrl);
    }
  }
  
  export default withRouter(MarkStep);
  