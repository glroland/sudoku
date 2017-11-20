import { Component, EventEmitter, Input, Output } from '@angular/core';
import { NgModel } from '@angular/forms';

@Component({
  selector: 'gamecell',
  templateUrl: './gamecell.html'
})
export class GameCell {
  @Input()  cellNumber: number;
  @Input()  initial: number = 0;

  isReadOnly() {
    if ((this.initial >= 1) && (this.initial <= 9)) {
      return true;
    }

    return false;
  }

  onClick(event) {
    if (!this.isReadOnly()) {
      var target = event.target || event.srcElement || event.currentTarget;
      var idAttr = target.attributes.id;
      var value = idAttr.nodeValue;

      target.className = "game-cell-button-on";
//      alert(value + " <" + idAttr.value + "> <" + this.initial + "> <" + target.attributes.class + "> <" + target.className + "> <" + target + ">");
    }
  }
}
