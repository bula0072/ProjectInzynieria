import {Component, Input} from '@angular/core';
import {TokenService} from "../services/token.service";
import {UserToken} from "../public/login/login.component";
import {Router} from "@angular/router";

@Component({
  selector: 'app-navigation-bar',
  templateUrl: './navigation-bar.component.html',
  styleUrls: ['./navigation-bar.component.css']
})
export class NavigationBarComponent {
  @Input() loggedIn: UserToken

  constructor(private tokenService: TokenService,
              private router: Router) {
  }

  async logout() {
    this.tokenService.signOut();
    const delay = ms => new Promise(res => setTimeout(res, ms));
    await delay(100)
    await this.router.navigate(['/flights'])
  }
}
