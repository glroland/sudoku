import { Component } from '@angular/core';
import { NavController } from 'ionic-angular';
import { Http, Headers, RequestOptions } from '@angular/http';
import { SingletonService } from '../../providers/singleton/singleton';
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';

@Component({
  selector: 'page-home',
  templateUrl: 'home.html'
})
export class HomePage {
  public txtGameGrid = [];

  constructor(public navCtrl: NavController, public http: Http, public singleton:SingletonService) {

      var url = "http://sudoku-svc-sudoku.a3c1.starter-us-west-1.openshiftapps.com/generate";

      http
        .get(url, {observe: 'response'})
        .subscribe(resp => {
          console.log(resp.text());
          var sudokuResponse = resp.json();

          var puzzle = sudokuResponse.puzzle.grid;
          var solution = sudokuResponse.solution.grid;

          for (var y=0; y<9; y++)
          {
            for (var x=0; x<9; x++)
            {
              var value = puzzle[y][x];
              if (value > 0)
              {
                console.log("X=" + x + " Y=" + y + " Val=" + value);

                var index = x + (y * 9);
                this.txtGameGrid[index] = value;
              }
            }
          }
        });

  }

  resetGame = function() {
    alert("hi");
  }
}
