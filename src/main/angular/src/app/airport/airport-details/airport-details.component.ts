import {Component, OnInit} from '@angular/core';
import {AirportService} from "../services/airport.service";
import {ActivatedRoute, ParamMap} from "@angular/router";
import {switchMap} from "rxjs/operators";
import {Observable} from "rxjs";

@Component({
  selector: 'app-airport-details',
  templateUrl: './airport-details.component.html',
  styleUrls: ['./airport-details.component.css']
})
export class AirportDetailsComponent implements OnInit {
  details: Observable<AirportDTO>

  constructor(private airportService: AirportService,
              private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.details = this.route.paramMap.pipe(
      switchMap((params: ParamMap) =>
        this.airportService.getAirportDetails(params.get('name')))
    )
  }

  changeActiveStatus(name: string) {
    this.airportService.setActiveStatus(name).subscribe()
    window.location.reload()
  }
}

export class AirportDTO {
  name: string
  capacity: number
  latitude: number
  longitude: number
  active: true
  user: UserDTO
}

export class UserDTO {

  constructor(username: string, password: string, email: string) {
    this.username = username;
    this.password = password;
    this.email = email;
  }

  username: string
  password: string
  email: string
  roles: string
}
