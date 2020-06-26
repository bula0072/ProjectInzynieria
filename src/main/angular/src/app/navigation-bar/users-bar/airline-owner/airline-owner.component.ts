import {Component, Input, OnInit} from '@angular/core';
import {UserToken} from "../../../public/login/login.component";

@Component({
  selector: 'app-airline-owner',
  templateUrl: './airline-owner.component.html',
  styleUrls: ['./airline-owner.component.css']
})
export class AirlineOwnerComponent implements OnInit {
  @Input() loggedIn: UserToken
  constructor() { }

  ngOnInit(): void {
  }

}
