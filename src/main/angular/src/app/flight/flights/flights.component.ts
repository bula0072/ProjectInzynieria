import {Component, OnInit} from '@angular/core';
import {TokenService} from "../../services/token.service";
import {FlightDTO, FlightService} from "../services/flight.service";
import {Observable} from "rxjs";
import {UserToken} from "../../public/login/login.component";

@Component({
  selector: 'app-flights',
  templateUrl: './flights.component.html',
  styleUrls: ['./flights.component.css']
})
export class FlightsComponent implements OnInit {
  user: UserToken
  flights: Observable<Array<FlightDTO>>

  constructor(private tokenService: TokenService,
              private flightService: FlightService) {
  }

  ngOnInit(): void {
    try {
      this.user = this.tokenService.getUser()
    } catch (e) {
      console.log('brak usera')
    }
    this.flights = this.flightService.getAllFlights()
  }

  isCorrect(user: UserToken, startDate: string) {
    if (user == null || user.roles.toString() != '[ROLE_USER]') return false
    return new Date(Date.now()).valueOf() < new Date(startDate).valueOf() - 1800000;
  }
}
