import React, {Component} from "react";
import {
  withRouter
} from 'react-router-dom';
import "../App.css";
import CameraStep from "./CameraStep";
import MarkStep from "./MarkStep";

const CONST_NOT_SUPPORTED = 0;
const CONST_STEP_1_CAMERA = 1;
const CONST_STEP_2_MARK = 2;

const CONST_IMAGE_WIDTH = 480;
const CONST_IMAGE_HEIGHT = CONST_IMAGE_WIDTH;

class ScanPage extends Component {

  currentStep = 0;
  imgData = null;
  
    constructor(props) {
      super(props);
      this.state = {
      };

      this.gameStatusRef = React.createRef();

      if (!this.supportUploadCheck()) {
        this.currentStep = CONST_NOT_SUPPORTED;
      }
      else {
        this.currentStep = CONST_STEP_1_CAMERA;
      }
      this.imgData = null;
    }
    
    render() {
      var stepRender = "";
      if (this.currentStep === CONST_STEP_1_CAMERA) {
        stepRender = <CameraStep width={CONST_IMAGE_WIDTH} height={CONST_IMAGE_HEIGHT} snapshotCallbackRef={this} snapshotCallback={this.snapshotCallback} />;
      }
      else if (this.currentStep === CONST_STEP_2_MARK) {
        stepRender = <MarkStep width={CONST_IMAGE_WIDTH} height={CONST_IMAGE_HEIGHT} markCallbackRef={this} markCallback={this.markCallback} imgData={this.imgData} />;        
      }
      
      return (
        <div className="options">
          <h1 className="welcome-title">Sudoku!</h1>
          <input ref={this.gameStatusRef} type="text" className="game-status" value="" /><br/>
          <br/>
          {stepRender}<br />
          <button className="welcome-button" onClick={() => this.mainmenu()}>Back</button><br />
          <br/>
          <br/>
          <div className="welcome-text">Copyright 2019 Lee Roland</div>
        </div>);
    }
  
    snapshotCallback(self, imageDataUrl) {
      self.imgData = imageDataUrl;

      self.currentStep = CONST_STEP_2_MARK;
      self.forceUpdate();
    }

    markCallback(self) {
      alert('markCallback');
    }

//       captureImg.src = dataUrl;
//      this.uploadImage(this.b64toBlob(dataUrl.replace(/^data:image\/(png|jpg);base64,/, "")));
/*    b64toBlob(b64Data, contentType='image/png', sliceSize=512) {
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
    } */

    supportUploadCheck() {
      return !!(navigator.mediaDevices && navigator.mediaDevices.getUserMedia);
    }

    setStatus(msg) {
      if (this.gameStatusRef.current != null) {
        this.gameStatusRef.current.value = msg;
      }
    }

    mainmenu() {
//      this.endCapture();
      this.props.history.push('/');
    }

/*    endCapture() {
      const constraints = {
        video: true
      };
      
      const video = document.querySelector('video');
      navigator.mediaDevices.getUserMedia(constraints).
        then((stream) => {video.srcObject = null});
      video.pause();
    } */
  }
  
  export default withRouter(ScanPage);
  