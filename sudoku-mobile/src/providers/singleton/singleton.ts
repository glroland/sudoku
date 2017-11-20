import { Injectable } from '@angular/core';

import { SudokuPuzzle } from '../../components/models/sudokupuzzle';

@Injectable()
export class SingletonService {
  public defaultServerURL:string = "http://misc:8080";
//  public defaultServerURL:string = "http://sudoku-svc-sudoku.apps.home.glroland.com";
  public serverURL:string = this.defaultServerURL;

  public currentPuzzle:SudokuPuzzle = null;
}
