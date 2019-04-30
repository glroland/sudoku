import React, {Component} from "react";
import {
  withRouter
} from 'react-router-dom';
import "../App.css";

class CameraStep extends Component {
    constructor(props) {
      super(props);
      this.state = {
      };
  
    }

    setStatus(msg) {
      if (this.gameStatusRef.current != null) {
        this.gameStatusRef.current.value = msg;
      }
    }
  
    render() {
      return (
        <div className="camera">
          <div>Step #1: Photograph Puzzle</div>
          <video autoPlay></video>
          <button className="welcome-button" onClick={() => this.snapshot()}>Capture</button><br />
        </div>);
    }
  
    componentDidMount() {
      this.beginCapture();
    }

    beginCapture() {
      const constraints = {
        video: {width: {exact: this.props.width}, height: {exact: this.props.height}},
        audio: false
      };
      
      const video = document.querySelector('video');
      
      navigator.mediaDevices.getUserMedia(constraints).then((stream) => {
          video.srcObject = stream;
//          this.renderCanvas();
        });
        video.play();
    }

    snapshot() {
      const video = document.querySelector('video');

      let captureDiv = document.createElement('canvas');
      captureDiv.width = video.videoWidth;
      captureDiv.height = video.videoHeight;
      let context = captureDiv.getContext('2d');
      context.drawImage(video, 0, 0);

      let dataUrl = captureDiv.toDataURL('image/png');

      this.props.snapshotCallback(this.props.snapshotCallbackRef, dataUrl);
    }
  }
  
  export default withRouter(CameraStep);
  