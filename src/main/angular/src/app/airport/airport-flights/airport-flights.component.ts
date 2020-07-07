import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {FlightDTO, FlightService} from "../../flight/services/flight.service";
import {Observable} from "rxjs";
import {AirlineDTO, AirlinesService} from "../../airline/services/airlines.service";
import {TokenService} from "../../services/token.service";

@Component({
  selector: 'app-airport-flights',
  templateUrl: './airport-flights.component.html',
  styleUrls: ['./airport-flights.component.css']
})
export class AirportFlightsComponent implements OnInit {
  flights: Observable<Array<FlightDTO>>
  user
  airlines: Observable<Array<AirlineDTO>>;

  constructor(
    private route: ActivatedRoute,
    private flightService: FlightService,
    private airlinesService: AirlinesService,
    private tokenService: TokenService
  ) {
  }

  ngOnInit(): void {
    this.user = this.tokenService.getUser().sub
    this.flights = this.flightService.getAllFlightsByAirport(this.user)
    this.airlines = this.airlinesService.getAllAirlines()
  }

  airlineFinder(airlineDTOS: Array<AirlineDTO>, user: string) {
    return airlineDTOS.find(t => t.user.username == user).name
  }
}
