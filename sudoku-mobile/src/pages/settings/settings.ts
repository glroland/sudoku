import { Component } from '@angular/core';
import { NavController } from 'ionic-angular';
import { SingletonService } from '../../providers/singleton/singleton';

@Component({
  selector: 'page-settings',
  templateUrl: 'settings.html'
})
export class SettingsPage {
  txtServerUrl:string = "";

  constructor(public navCtrl: NavController, public singleton:SingletonService) {
    this.txtServerUrl = singleton.serverURL;
  }

}
