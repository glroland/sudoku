import { Component } from '@angular/core';
import { NavController } from 'ionic-angular';
import { SingletonService } from '../../providers/singleton/singleton';

@Component({
  selector: 'page-about',
  templateUrl: 'about.html'
})
export class AboutPage {
  txtServerUrl:string = singleton.serverURL;

  constructor(public navCtrl: NavController, public singleton:SingletonService) {
  }

}
