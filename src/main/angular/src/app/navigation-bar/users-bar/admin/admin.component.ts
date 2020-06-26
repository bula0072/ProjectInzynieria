import {Component, Input, OnInit} from '@angular/core';
import {UserToken} from "../../../public/login/login.component";

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {
  @Input() loggedIn: UserToken
  constructor() { }

  ngOnInit(): void {
  }

}
