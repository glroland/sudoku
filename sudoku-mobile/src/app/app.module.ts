import { NgModule, ErrorHandler } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { IonicApp, IonicModule, IonicErrorHandler } from 'ionic-angular';
import { MyApp } from './app.component';

import { HttpModule } from '@angular/http';

import { SettingsPage } from '../pages/settings/settings';
import { PlayPage } from '../pages/play/play';
import { TabsPage } from '../pages/tabs/tabs';

import { GameCell } from '../components/gamecell/gamecell';
import { GameBoardComponent } from '../components/gameboard/gameboard'

import { StatusBar } from '@ionic-native/status-bar';
import { SplashScreen } from '@ionic-native/splash-screen';

import { SingletonService } from '../providers/singleton/singleton';

@NgModule({
  declarations: [
    MyApp,
    SettingsPage,
    PlayPage,
    TabsPage,
    GameCell,
    GameBoardComponent
  ],
  imports: [
    BrowserModule,
    HttpModule,
    IonicModule.forRoot(MyApp)
  ],
  bootstrap: [IonicApp],
  entryComponents: [
    MyApp,
    SettingsPage,
    PlayPage,
    TabsPage,
    GameCell,
    GameBoardComponent
  ],
  providers: [
    StatusBar,
    SplashScreen,
    SingletonService,
    {provide: ErrorHandler, useClass: IonicErrorHandler}
  ]
})
export class AppModule {

}
