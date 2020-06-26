import {Component, Input, OnInit} from '@angular/core';
import {TokenService} from "../services/token.service";
import {UserToken} from "../public/login/login.component";

@Component({
  selector: 'app-navigation-bar',
  templateUrl: './navigation-bar.component.html',
  styleUrls: ['./navigation-bar.component.css']
})
export class NavigationBarComponent{
  @Input() loggedIn: UserToken

  constructor(private tokenService: TokenService) {}

  logout() {
    this.tokenService.signOut();
    window.location.reload();
  }
}
