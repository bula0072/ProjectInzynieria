import {Component, OnInit} from '@angular/core';
import {Observable} from "rxjs";
import {AirplaneDTO, AirplaneService} from "../../airplanes/services/airplane.service";
import {ActivatedRoute, ParamMap} from "@angular/router";
import {switchMap} from "rxjs/operators";
import {AirportDTO} from "../../airport/airport-details/airport-details.component";
import {AirportService} from "../../airport/services/airport.service";

@Component({
  selector: 'app-new-flight',
  templateUrl: './new-flight.component.html',
  styleUrls: ['./new-flight.component.css']
})
export class NewFlightComponent implements OnInit {
  form: any = {};
  planes: Observable<Array<AirplaneDTO>>
  airports: Observable<Array<AirportDTO>>

  constructor(private route: ActivatedRoute,
              private airplaneService: AirplaneService,
              private airportService: AirportService) {
  }

  ngOnInit(): void {
    this.planes = this.route.paramMap.pipe(
      switchMap((params: ParamMap) =>
        this.airplaneService.getAirplanes(params.get('name')))
    )
    this.airports = this.airportService.getAllAirports();
  }

  onSubmit() {

  }
}
