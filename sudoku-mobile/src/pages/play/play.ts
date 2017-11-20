import { Component } from '@angular/core';
import { NavController } from 'ionic-angular';
import { Http } from '@angular/http';
import { SingletonService } from '../../providers/singleton/singleton';
import { GameBoardComponent } from '../../components/gameboard/gameboard';
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';

@Component({
  selector: 'page-play',
  templateUrl: 'play.html'
})
export class PlayPage {
  public gameboard: GameBoardComponent = new GameBoardComponent();
  public http: Http;
  public singleton: SingletonService;
  public puzzleGrid: any = [];

  constructor(public navCtrl: NavController, public inHttp: Http, public inSingleton:SingletonService) {
    this.http = inHttp;
    this.singleton = inSingleton;

    this.onNew();
  }

  onReset = function() {
    this.gameboard.reset();
  }

  onCheck = function() {
    alert("check");
  }

  bind = function(board) {
    for (var y=0; y<9; y++)
    {
      for (var x=0; x<9; x++)
      {
        var value = board[y][x];
        if (value > 0)
        {
          console.log("X=" + x + " Y=" + y + " Val=" + value);

          var index = x + (y * 9);
          this.puzzleGrid[index] = value;
        }
      }
    }
  }

  onNew = function() {
    this.onReset();

    this.http
      .get(this.singleton.serverURL + "/generate")
      .subscribe(resp => {
        console.log(resp.text());
        var sudokuResponse = resp.json();

        var puzzle = sudokuResponse.puzzle.grid;
        var solution = sudokuResponse.solution.grid;

        this.bind(puzzle);
      },
      err => {
            alert("SUDOKU-SVC Invocation Error: " + err);
        });

  }
}
