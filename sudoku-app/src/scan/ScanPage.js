import React, {Component} from "react";
import {
  withRouter
} from 'react-router-dom';
import "../App.css";

class ScanPage extends Component {
    localStream = null;

    constructor(props) {
      super(props);
      this.state = {
      };
  
    }
  
    supportUploadCheck() {
      return !!(navigator.mediaDevices && navigator.mediaDevices.getUserMedia);
    }
    render() {
      let statusMsg;
      if (!this.supportUploadCheck()) {
        statusMsg = "Feature Not Supported or Allowed...";
      } else {
        statusMsg = "Ready for Upload!"
      }
        
      return (
        <div className="options">
          <h1 className="welcome-title">Sudoku!</h1>
          {statusMsg}<br />
          <br/>
          <div className="scan-work">
          </div>
          <canvas id="previewCanvas" />
          <img id="captureImg" alt="" />
          <br/>
          <button className="welcome-button" onClick={() => this.snapshot()}>Capture</button><br />
          <button className="welcome-button" onClick={() => this.mainmenu()}>Back</button><br />
          <br/>
          <br/>
          <div className="welcome-text">Copyright 2019 Lee Roland</div>
          <video autoPlay></video>
        </div>);
    }
  
    snapshot() {
      const video = document.querySelector('video');

      let captureDiv = document.createElement('canvas');
      captureDiv.width = video.videoWidth;
      captureDiv.height = video.videoHeight;
      let context = captureDiv.getContext('2d');
      context.drawImage(video, 0, 0);

      let captureImg = document.getElementById('captureImg');
      let dataUrl = captureDiv.toDataURL('image/png');
      captureImg.src = dataUrl;

      this.uploadImage(this.b64toBlob(dataUrl.replace(/^data:image\/(png|jpg);base64,/, "")));
    }

    b64toBlob(b64Data, contentType='image/png', sliceSize=512) {
      const byteCharacters = atob(b64Data);
      const byteArrays = [];
    
      for (let offset = 0; offset < byteCharacters.length; offset += sliceSize) {
        const slice = byteCharacters.slice(offset, offset + sliceSize);
    
        const byteNumbers = new Array(slice.length);
        for (let i = 0; i < slice.length; i++) {
          byteNumbers[i] = slice.charCodeAt(i);
        }
    
        const byteArray = new Uint8Array(byteNumbers);
        byteArrays.push(byteArray);
      }
    
      const blob = new Blob(byteArrays, {type: contentType});
      return blob;
    }

    uploadImage(imageBytes) {
      var serviceUrl = process.env.REACT_APP_SVC_URL;
      if ((serviceUrl === undefined))
        serviceUrl = 'http://svc-sudoku.apps3.home.glroland.com';
      console.log("Service URL = " + serviceUrl);
  
      var formData = new FormData();
      //var blob = new Blob(imageBytes, { type: "image/png"});
      formData.append("file", imageBytes);

      serviceUrl = 'http://localhost:5002';
      fetch(serviceUrl + '/extract', {
        method: 'POST',
        body: formData
      })
      .then(results => {
        console.log("Raw Upload Results = " + results);
        return results;
      }).then(data => {
        console.log("Raw Upload Data = " + data);
      });

    }

    componentDidMount() {
      this.beginCapture();
    }
 
    componentWillUnmount() {
      this.endCapture();
    }

    beginCapture() {
      const constraints = {
        video: {width: {exact: 480}, height: {exact: 480}},
        audio: false
      };
      
      const video = document.querySelector('video');
      
      navigator.mediaDevices.getUserMedia(constraints).then((stream) => {
          this.localStream = stream;
          video.srcObject = stream;
          this.renderCanvas();
        });
        video.play();
    }

    renderCanvas() {
      const video = document.querySelector('video');
      if (video == undefined) 
        return;

      let previewCanvas = document.getElementById('previewCanvas');

      previewCanvas.width = video.videoWidth;
      previewCanvas.height = video.videoHeight;

      let context = previewCanvas.getContext('2d');
      context.drawImage(video, 0, 0);
      context.beginPath();
      let blockWidth = previewCanvas.width / 9;
      let blockHeight = previewCanvas.height / 9;
      var x, y;
      for (x = 0; x < 9; x++) {
        for (y = 0; y < 9; y++) {
          context.rect((x * blockWidth), (y * blockHeight), ((x + 1) * blockWidth) - 1, ((y + 1) * blockHeight) - 1);
        }
      }
      context.strokeStyle = 'red';
      context.lineWidth = 1;
      context.stroke();

      requestAnimationFrame(() => {
        this.renderCanvas();
      });
    }

    endCapture() {
      const constraints = {
        video: true
      };
      
      const video = document.querySelector('video');
      navigator.mediaDevices.getUserMedia(constraints).
        then((stream) => {video.srcObject = null});
      video.pause();
    }

    mainmenu() {
      this.endCapture();
      this.props.history.push('/');
    }
  }
  
  export default withRouter(ScanPage);
  