import {Component, Input, OnInit} from '@angular/core';
import {UserToken} from "../../public/login/login.component";
import {TokenService} from "../../services/token.service";

@Component({
  selector: 'app-users-bar',
  templateUrl: './users-bar.component.html',
  styleUrls: ['./users-bar.component.css']
})
export class UsersBarComponent implements OnInit {
  @Input() loggedIn: UserToken
  constructor(
    private tokenService: TokenService
  ) { }

  ngOnInit(): void {
  }

}
