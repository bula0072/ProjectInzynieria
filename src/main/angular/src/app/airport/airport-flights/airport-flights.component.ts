import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, ParamMap} from "@angular/router";
import {switchMap} from "rxjs/operators";
import {FlightDTO, FlightService} from "../../flight/services/flight.service";
import {Observable} from "rxjs";
import {AirlinesService} from "../../airline/services/airlines.service";
import {TokenService} from "../../services/token.service";

@Component({
  selector: 'app-airport-flights',
  templateUrl: './airport-flights.component.html',
  styleUrls: ['./airport-flights.component.css']
})
export class AirportFlightsComponent implements OnInit {
  flights: Observable<Array<FlightDTO>>
user
  constructor(
    private route: ActivatedRoute,
    private flightService: FlightService,
    private airlinesService: AirlinesService,
    private tokenService: TokenService
  ) { }

  ngOnInit(): void {
    this.flights = this.route.paramMap.pipe(
      switchMap((p: ParamMap) =>
      this.flightService.getAllFlights())
    )
    this.user = this.tokenService.getUser().sub
  }

  findAirline(user: string) {
    return this.airlinesService.getAirlineByUser(user)
  }
}
