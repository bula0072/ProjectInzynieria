import {Component, OnInit} from '@angular/core';
import {Observable} from "rxjs";
import {AirportDTO} from "../airport-details/airport-details.component";
import {AirportService} from "../services/airport.service";
import {ActivatedRoute, ParamMap, Router} from "@angular/router";
import {switchMap} from "rxjs/operators";

@Component({
  selector: 'app-edit-airport',
  templateUrl: './edit-airport.component.html',
  styleUrls: ['./edit-airport.component.css']
})

export class EditAirportComponent implements OnInit {
  details: Observable<AirportDTO>
  form;
  longitude: number;
  latitude: number;
  capacity: number;
  airlineName: string;

  constructor(private airportService: AirportService,
              private route: ActivatedRoute,
              private router: Router) {
  }

  ngOnInit(): void {
    this.details = this.route.paramMap.pipe(
      switchMap((params: ParamMap) =>
        this.airportService.getAirport(params.get('name')))
    )
  }

  async save(detail: AirportDTO) {
    let airport = new AirportChange(this.airlineName, this.capacity, this.latitude, this.longitude)
    await this.airportService.editAirport(detail.name, airport).subscribe()
    await this.router.navigate(['/airportDetails/' + detail.user.username])
  }
}

export class AirportChange {
  name: string
  capacity: number
  latitude: number
  longitude: number

  constructor(name: string, capacity: number, latitude: number, longitude: number) {
    this.name = name;
    this.capacity = capacity;
    this.latitude = latitude;
    this.longitude = longitude;
  }
}
