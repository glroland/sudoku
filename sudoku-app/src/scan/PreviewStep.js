import React, {Component} from "react";
import {
  withRouter
} from 'react-router-dom';
import "../App.css";

class PreviewStep extends Component {
    constructor(props) {
      super(props);
      this.state = {
      };
  
      this.imgRef = React.createRef();
    }
  
    render() {
      return (
        <div className="camera">
          <div>Step #3: Preview Photograph</div>
          <img ref={this.imgRef} width={this.props.width} height={this.props.height} src={this.props.imgData} alt="" />
          <button className="welcome-button" onClick={() => this.retry()}>Retry</button><br />
          <br/>
          <button className="welcome-button" onClick={() => this.confirm()}>Confirm</button><br />
        </div>);
    }

    retry() {
        this.props.previewCallback(this.props.previewCallbackRef, false);
    }

    confirm() {
        this.props.previewCallback(this.props.previewCallbackRef, true);
    }
  }
  
  export default withRouter(PreviewStep);
  