import React, {Component} from "react";
import {
  withRouter
} from 'react-router-dom';
import "../App.css";

class UploadStep extends Component {
    constructor(props) {
      super(props);
      this.state = {
      };
  
      this.imgRef = React.createRef();
      this.parseResponse = this.parseResponse.bind(this);

      this.puzzle = [
          [0, 0, 0, 0, 0, 0, 0, 0, 0],
          [0, 0, 0, 0, 0, 0, 0, 0, 0],
          [0, 0, 0, 0, 0, 0, 0, 0, 0],
          [0, 0, 0, 0, 0, 0, 0, 0, 0],
          [0, 0, 0, 0, 0, 0, 0, 0, 0],
          [0, 0, 0, 0, 0, 0, 0, 0, 0],
          [0, 0, 0, 0, 0, 0, 0, 0, 0],
          [0, 0, 0, 0, 0, 0, 0, 0, 0],
          [0, 0, 0, 0, 0, 0, 0, 0, 0]
        ];
    }
  
    square(x, y) {
        let v = this.puzzle[x][y];
        if (v === 0) {
            return ' ';
        }
        return v;
    }

    render() {
      return (
        <div className="camera">
          <div>Step #4: Upload and Analyze Photograph</div>
          <table align="center" border="0">
          <tr>
              <td align="right"><img ref={this.imgRef} width={this.props.width / 2} height={this.props.height / 2} src={this.props.imgData} alt=""/></td>
              <td align="left"><table align="center" width={this.props.width / 2} border="1">
              <tr>
                  <td align="center" height="20">{this.square(0,0)}</td>
                  <td align="center">{this.square(1,0)}</td>
                  <td align="center">{this.square(2,0)}</td>
                  <td align="center">{this.square(3,0)}</td>
                  <td align="center">{this.square(4,0)}</td>
                  <td align="center">{this.square(5,0)}</td>
                  <td align="center">{this.square(6,0)}</td>
                  <td align="center">{this.square(7,0)}</td>
                  <td align="center">{this.square(8,0)}</td>
              </tr>
              <tr>
                  <td align="center" height="20">{this.square(0,1)}</td>
                  <td align="center">{this.square(1,1)}</td>
                  <td align="center">{this.square(2,1)}</td>
                  <td align="center">{this.square(3,1)}</td>
                  <td align="center">{this.square(4,1)}</td>
                  <td align="center">{this.square(5,1)}</td>
                  <td align="center">{this.square(6,1)}</td>
                  <td align="center">{this.square(7,1)}</td>
                  <td align="center">{this.square(8,1)}</td>
              </tr>
              <tr>
                  <td align="center" height="20">{this.square(0,2)}</td>
                  <td align="center">{this.square(1,2)}</td>
                  <td align="center">{this.square(2,2)}</td>
                  <td align="center">{this.square(3,2)}</td>
                  <td align="center">{this.square(4,2)}</td>
                  <td align="center">{this.square(5,2)}</td>
                  <td align="center">{this.square(6,2)}</td>
                  <td align="center">{this.square(7,2)}</td>
                  <td align="center">{this.square(8,2)}</td>
              </tr>
              <tr>
                  <td align="center" height="20">{this.square(0,3)}</td>
                  <td align="center">{this.square(1,3)}</td>
                  <td align="center">{this.square(2,3)}</td>
                  <td align="center">{this.square(3,3)}</td>
                  <td align="center">{this.square(4,3)}</td>
                  <td align="center">{this.square(5,3)}</td>
                  <td align="center">{this.square(6,3)}</td>
                  <td align="center">{this.square(7,3)}</td>
                  <td align="center">{this.square(8,3)}</td>
              </tr>
              <tr>
                  <td align="center" height="20">{this.square(0,4)}</td>
                  <td align="center">{this.square(1,4)}</td>
                  <td align="center">{this.square(2,4)}</td>
                  <td align="center">{this.square(3,4)}</td>
                  <td align="center">{this.square(4,4)}</td>
                  <td align="center">{this.square(5,4)}</td>
                  <td align="center">{this.square(6,4)}</td>
                  <td align="center">{this.square(7,4)}</td>
                  <td align="center">{this.square(8,4)}</td>
              </tr>
              <tr>
                  <td align="center" height="20">{this.square(0,5)}</td>
                  <td align="center">{this.square(1,5)}</td>
                  <td align="center">{this.square(2,5)}</td>
                  <td align="center">{this.square(3,5)}</td>
                  <td align="center">{this.square(4,5)}</td>
                  <td align="center">{this.square(5,5)}</td>
                  <td align="center">{this.square(6,5)}</td>
                  <td align="center">{this.square(7,5)}</td>
                  <td align="center">{this.square(8,5)}</td>
              </tr>
              <tr>
                  <td align="center" height="20">{this.square(0,6)}</td>
                  <td align="center">{this.square(1,6)}</td>
                  <td align="center">{this.square(2,6)}</td>
                  <td align="center">{this.square(3,6)}</td>
                  <td align="center">{this.square(4,6)}</td>
                  <td align="center">{this.square(5,6)}</td>
                  <td align="center">{this.square(6,6)}</td>
                  <td align="center">{this.square(7,6)}</td>
                  <td align="center">{this.square(8,6)}</td>
              </tr>
              <tr>
                  <td align="center" height="20">{this.square(0,7)}</td>
                  <td align="center">{this.square(1,7)}</td>
                  <td align="center">{this.square(2,7)}</td>
                  <td align="center">{this.square(3,7)}</td>
                  <td align="center">{this.square(4,7)}</td>
                  <td align="center">{this.square(5,7)}</td>
                  <td align="center">{this.square(6,7)}</td>
                  <td align="center">{this.square(7,7)}</td>
                  <td align="center">{this.square(8,7)}</td>
              </tr>
              <tr>
                  <td align="center" height="20">{this.square(0,8)}</td>
                  <td align="center">{this.square(1,8)}</td>
                  <td align="center">{this.square(2,8)}</td>
                  <td align="center">{this.square(3,8)}</td>
                  <td align="center">{this.square(4,8)}</td>
                  <td align="center">{this.square(5,8)}</td>
                  <td align="center">{this.square(6,8)}</td>
                  <td align="center">{this.square(7,8)}</td>
                  <td align="center">{this.square(8,8)}</td>
              </tr>
              </table></td>
          </tr>
          </table>
          
          
          <button className="welcome-button" onClick={() => this.play()}>Play</button><br />
        </div>);
    }
  
    componentDidMount() {
        this.uploadImage(this.b64toBlob(this.props.imgData.replace(/^data:image\/(png|jpg);base64,/, "")));
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
      .then(function(response) {
        // response.status = 200
        return response.json();
      }).then( this.parseResponse);
    }

    parseResponse(json) {
        var x, y;
        for (x = 0; x < 9; x++) {
            for (y = 0; y < 9; y++) {
                this.puzzle[x][y] = json.puzzle.grid[y][x];
            }
        }
        this.forceUpdate();
    }
  }
  
  export default withRouter(UploadStep);
  