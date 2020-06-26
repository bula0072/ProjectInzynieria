import {Component, OnInit} from '@angular/core';
import {AuthorizationService} from "../../services/authorization.service";
import {Router} from "@angular/router";
import {TokenService} from "../../services/token.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  form: any = {};
  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  // roles: string[] = [];
  private roles: Array<string>;

  constructor(private authService: AuthorizationService,
              private tokenService: TokenService,
              private router: Router) {
  }

  ngOnInit() {
    if (this.tokenService.getToken()) {
      this.isLoggedIn = true;
      this.roles = this.tokenService.getUser().roles
    }
  }

  onSubmit() {
    this.authService.login(this.form).subscribe(
      data => {
        this.tokenService.saveToken(data.headers.get("authorization"));
        let token: UserToken = JSON.parse(atob(data.headers.get("authorization").split('.')[1]))

        this.tokenService.saveUser(token)
        this.isLoginFailed = false;
        this.isLoggedIn = true;
        this.roles = this.tokenService.getUser().roles
        this.router.navigate(['/'])
      },
      err => {
        console.log('fail');
        this.isLoginFailed = true
      }
    )
  }
}

export class UserToken {
  sub: string
  roles: Array<string>
  exp: number
}

