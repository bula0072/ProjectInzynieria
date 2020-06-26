import {Component, Input, OnInit} from '@angular/core';
import {UserToken} from "../../../public/login/login.component";
import {Router} from "@angular/router";
import {TokenService} from "../../../services/token.service";

@Component({
  selector: 'app-airport-owner',
  templateUrl: './airport-owner.component.html',
  styleUrls: ['./airport-owner.component.css']
})
export class AirportOwnerComponent implements OnInit {
  @Input() loggedIn: UserToken

  constructor(
    private router: Router,
    private tokenService: TokenService
  ) {
  }

  ngOnInit(): void {
  }

  getDetails() {
this.router.navigate(['/airportDetails'])
  }
}
