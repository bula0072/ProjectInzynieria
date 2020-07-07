import {Component, OnInit} from '@angular/core';
import {Observable} from "rxjs";
import {AirplaneDTO, AirplaneService} from "../../airplanes/services/airplane.service";
import {ActivatedRoute, ParamMap, Router} from "@angular/router";
import {switchMap} from "rxjs/operators";
import {AirportDTO} from "../../airport/airport-details/airport-details.component";
import {AirportService} from "../../airport/services/airport.service";
import {FlightAddDTO, FlightService} from "../services/flight.service";
import {TokenService} from "../../services/token.service";

@Component({
  selector: 'app-new-flight',
  templateUrl: './new-flight.component.html',
  styleUrls: ['./new-flight.component.css']
})
export class NewFlightComponent implements OnInit {
  form: any = {};
  planes: Observable<Array<AirplaneDTO>>
  airports: Observable<Array<AirportDTO>>

  cost: number;
  startAirport: string;
  startDate: string;
  endAirport: string;
  endDate: string;
  airplane: number;

  constructor(private route: ActivatedRoute,
              private airplaneService: AirplaneService,
              private airportService: AirportService,
              private flightService: FlightService,
              private router: Router,
              private tokenService: TokenService) {
  }

  ngOnInit(): void {
    this.planes = this.route.paramMap.pipe(
      switchMap((params: ParamMap) =>
        this.airplaneService.getAirplanes(params.get('name')))
    )
    this.airports = this.airportService.getAllAirports();
  }

  async addFlight() {
    this.flightService.addNewFlight(new FlightAddDTO(
      this.cost,
      this.startDate,
      this.endDate,
      this.startAirport,
      this.endAirport,
      this.airplane
    )).subscribe()
    const delay = ms => new Promise(res => setTimeout(res, ms));
    await delay(100)
    await this.router.navigate(['/airlines/flights/' + this.tokenService.getUser().sub])
  }
}


