import { Component } from '@angular/core';
import { EventEmitter } from '@angular/core';
import { ControlValueAccessor } from '@angular/forms';

@Component({
  selector: 'gameboard-component',
  templateUrl: 'gameboard.html',
  inputs: ['gameGrid'],
  outputs: ['gameGridChange']
})
export class GameBoardComponent implements ControlValueAccessor {
  public gameGrid;
  public gameGridChange: EventEmitter<any> = new EventEmitter();

  constructor() {
  }

  reset() {  
  }

  ionViewDidLoad() {
  }

  isClicked(val) {
    alert("isClicked");
  }

  writeValue(value: any): void {
  }

  registerOnChange(fn: (_: any) => void): void {
    alert("isClicked");
  }

  registerOnTouched(fn: any): void {
  }

  setDisabledState(isDisabled: boolean): void {
  }
}
