import {Component, OnInit} from '@angular/core';
import {TokenService} from "./services/token.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent{
  constructor(private tokenService: TokenService) {}

  get loggedIn(){
    return this.tokenService.getUser()
  }


}
