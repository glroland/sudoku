import { Injectable } from '@angular/core';

@Injectable()
export class SingletonService {
  public defaultServerURL:string = "http://misc:8080";
  public serverURL:string = this.defaultServerURL;

}
