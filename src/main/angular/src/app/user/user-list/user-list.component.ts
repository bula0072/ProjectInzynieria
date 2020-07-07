import {Component, OnInit} from '@angular/core';
import {TokenService} from "../../services/token.service";
import {UserToken} from "../../public/login/login.component";
import {MainService} from "../../main/services/main.service";
import {Observable} from "rxjs";
import {UserDTO} from "../../airport/airport-details/airport-details.component";

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {
  user: UserToken
  users: Observable<Array<UserDTO>>

  constructor(private tokenService: TokenService,
              private mainService: MainService) {
  }

  ngOnInit(): void {
    try {
      this.user = this.tokenService.getUser()
    } catch (e) {
      console.log('brak usera')
    }
    this.users = this.mainService.getAllUser()
  }
}
