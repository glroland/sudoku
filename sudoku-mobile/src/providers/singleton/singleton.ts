import { Injectable } from '@angular/core';

@Injectable()
export class SingletonService {
  public serverURL:string = "http://sudoku-svc-sudoku.a3c1.starter-us-west-1.openshiftapps.com";

}
