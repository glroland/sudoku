import React, {Component} from "react";
import { withRouter } from '../withRouter';
import "../App.css";
import CameraStep from "./CameraStep";
import MarkStep from "./MarkStep";
import PreviewStep from "./PreviewStep";
import UploadStep from "./UploadStep";

const CONST_NOT_SUPPORTED = 0;
const CONST_STEP_1_CAMERA = 1;
const CONST_STEP_2_MARK = 2;
const CONST_STEP_3_PREVIEW = 3;
const CONST_STEP_4_UPLOAD = 4;

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
      this.markedImgData = null;
    }
    
    render() {
      var stepRender = "";
      if (this.currentStep === CONST_STEP_1_CAMERA) {
        stepRender = <CameraStep width={CONST_IMAGE_WIDTH} height={CONST_IMAGE_HEIGHT} snapshotCallbackRef={this} snapshotCallback={this.snapshotCallback} />;
      }
      else if (this.currentStep === CONST_STEP_2_MARK) {
        stepRender = <MarkStep width={CONST_IMAGE_WIDTH} height={CONST_IMAGE_HEIGHT} markCallbackRef={this} markCallback={this.markCallback} imgData={this.imgData} />;        
      }
      else if (this.currentStep === CONST_STEP_3_PREVIEW) {
        stepRender = <PreviewStep  width={CONST_IMAGE_WIDTH} height={CONST_IMAGE_HEIGHT} previewCallbackRef={this} previewCallback={this.previewCallback} imgData={this.markedImgData} />; 
      }
      else if (this.currentStep === CONST_STEP_4_UPLOAD) {
        stepRender = <UploadStep  width={CONST_IMAGE_WIDTH} height={CONST_IMAGE_HEIGHT} uploadCallbackRef={this} uploadCallback={this.uploadCallback} imgData={this.markedImgData} />; 
      }
      
      return (
        <div className="options">
          <h1 className="welcome-title">Sudoku!</h1>
          <input ref={this.gameStatusRef} type="text" className="game-status" value="" readOnly /><br/>
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

    markCallback(self, imageDataUrl) {
      self.markedImgData = imageDataUrl;

      self.currentStep = CONST_STEP_3_PREVIEW;
      self.forceUpdate();
    }

    previewCallback(self, confirmedFlag) {
      if (!confirmedFlag) {
        self.currentStep = CONST_STEP_2_MARK;
      }
      else {
        self.currentStep = CONST_STEP_4_UPLOAD;
      }
      self.forceUpdate();
    }

    uploadCallback(self, puzzle) {
      alert("upload callback" + puzzle[0][0]);
    }

    supportUploadCheck() {
      return !!(navigator.mediaDevices && navigator.mediaDevices.getUserMedia);
    }

    setStatus(msg) {
      if (this.gameStatusRef.current != null) {
        this.gameStatusRef.current.value = msg;
      }
    }

    mainmenu() {
      this.props.router.navigate('/');
    }

  }
  
  export default withRouter(ScanPage);
  