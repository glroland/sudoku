import React, {Component} from "react";
import {
  withRouter
} from 'react-router-dom';
import "../App.css";
import Board from "./Board";

class UploadStep extends Component {
    constructor(props) {
      super(props);
      this.state = {
      };
  
      this.imgRef = React.createRef();
      this.boardRef = React.createRef();
      this.solutionRef = React.createRef();
    }

    render() {
      return (
        <div className="camera">
          <div>Step #4: Upload and Analyze Photograph</div>
            <table align="center" border="0">
              <tbody>            
                <tr>
                    <td align="right"><img ref={this.imgRef} width={this.props.width / 2} height={this.props.height / 2} src={this.props.imgData} alt=""/></td>
                    <td align="left"><Board ref={this.boardRef} width={this.props.width / 2} height={this.props.height / 2} /></td>
                </tr>
                <tr>
                  <td align="center"><Board ref={this.solutionRef}  width={this.props.width / 2} height={this.props.height / 2} /></td>
                </tr>
              </tbody>
            </table>

            <button className="welcome-button" onClick={() => this.play()}>Play</button><br />
        </div>);
    }
  
    componentDidMount() {
        this.uploadImage(this.b64toBlob(this.props.imgData.replace(/^data:image\/(png|jpg);base64,/, "")));
//        this.solve();
    }
  
    play() {
        this.props.uploadCallback(this.props.uploadCallbackRef, this.puzzle);
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

    solve() {
      let p = Array(9);
      for (var y=0; y<9; y++) {
        p[y] = Array(9).fill(0);
      }
      
      let pJson = JSON.parse(JSON.stringify(p));
  
      var serviceUrl = process.env.REACT_APP_SVC_URL;
      if ((serviceUrl === undefined))
        serviceUrl = 'http://svc-sudoku.apps3.home.glroland.com';
      console.log("Service URL = " + serviceUrl);
  
      fetch(serviceUrl + '/solve', {
        method: "POST",
        mode: "cors",
        cache: "no-cache",
        credentials: "omit", // include, *same-origin, omit
        headers: {
            "Content-Type": "application/json",
            // "Content-Type": "application/x-www-form-urlencoded",
        },
        redirect: "follow", // manual, *follow, error
        referrer: "no-referrer", // no-referrer, *client
        body: JSON.stringify(pJson), // body data type must match "Content-Type" header
    })
      .then(results => {
        let sol = results.json();
        console.log("Results: " + sol);
//        this.solutionRef.current.updateWithJsonResponse(sol);
        return sol;
      }).then(data => {
        this.solutionGrid = data.grid;
        console.log("Solution = " + this.solutionGrid);
  
        this.forceUpdate();
      }); //.catch(error => {
        //console.log("An error occurred while contacting server for new puzzle: " + error);
        //alert(error);
      //})
    }

    uploadImage(imageBytes) {
      this.boardRef.current.clear();

      var serviceUrl = undefined;
      if ((serviceUrl === undefined))
        serviceUrl = 'http://ocrsvc-sudoku.apps3.home.glroland.com';
      console.log("Service URL = " + serviceUrl);
  
      var formData = new FormData();
      //var blob = new Blob(imageBytes, { type: "image/png"});
      formData.append("file", imageBytes);

      fetch(serviceUrl + '/extract', {
        method: 'POST',
        body: formData
      })
      .then(function(response) {
        // response.status = 200
        return response.json();
      }).then( this.boardRef.current.updateWithJsonResponse);
    }
  }
  
  export default withRouter(UploadStep);
  