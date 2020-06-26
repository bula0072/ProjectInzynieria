import {Component, Input, OnInit} from '@angular/core';
import {UserToken} from "../../../public/login/login.component";

@Component({
  selector: 'app-normal-user',
  templateUrl: './normal-user.component.html',
  styleUrls: ['./normal-user.component.css']
})
export class NormalUserComponent implements OnInit {
  @Input() loggedIn: UserToken
  constructor() { }

  ngOnInit(): void {
  }

}
