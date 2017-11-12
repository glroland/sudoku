import { Component } from '@angular/core';

import { SettingsPage } from '../settings/settings';
import { PlayPage } from '../play/play';

@Component({
  templateUrl: 'tabs.html'
})
export class TabsPage {

  tab1Root = PlayPage;
  tab2Root = SettingsPage;

  constructor() {

  }
}
