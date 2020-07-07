import {Component, OnInit} from '@angular/core';
import {FlightDTO, FlightService} from "../../flight/services/flight.service";
import {ActivatedRoute, ParamMap} from "@angular/router";
import {Observable} from "rxjs";
import {AirlinesService} from "../services/airlines.service";
import {switchMap} from "rxjs/operators";

@Component({
  selector: 'app-airline-flights',
  templateUrl: './airline-flights.component.html',
  styleUrls: ['./airline-flights.component.css']
})
export class AirlineFlightsComponent implements OnInit {
  flights: Observable<Array<FlightDTO>>

  constructor(
    private flightService: FlightService,
    private airlinesService: AirlinesService,
    private route: ActivatedRoute
  ) {
  }

  ngOnInit(): void {
    this.flights = this.route.paramMap.pipe(
      switchMap((p: ParamMap) =>
        this.flightService.getAllFlightsByAirline(p.get('name')))
    )
  }

  removeFlight(id: number) {
    this.flightService.deleteFlight(id).subscribe()
    window.location.reload()
  }

  dateCreator(startDate: string, endDate: string) {
    let stDate = new Date(startDate).valueOf()
    let enDate = new Date(endDate).valueOf()
    let now = new Date(Date.now()).valueOf()
    return !(stDate - 1800000 <= now
      && enDate + 1800000 >= now);
  }
}
