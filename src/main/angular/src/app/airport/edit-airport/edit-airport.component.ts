import {Component, OnInit} from '@angular/core';
import {Observable} from "rxjs";
import {AirportDTO} from "../airport-details/airport-details.component";
import {AirportService} from "../services/airport.service";
import {ActivatedRoute, ParamMap, Router} from "@angular/router";
import {switchMap} from "rxjs/operators";
import {FormControl, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-edit-airport',
  templateUrl: './edit-airport.component.html',
  styleUrls: ['./edit-airport.component.css']
})

export class EditAirportComponent implements OnInit {
  details: Observable<AirportDTO>
  form;

  constructor(private airportService: AirportService,
              private route: ActivatedRoute,
              private router: Router) {
  }

  ngOnInit(): void {
    this.details = this.route.paramMap.pipe(
      switchMap((params: ParamMap) =>
        this.airportService.getAirport(params.get('name')))
    )
    this.details.subscribe(t => {
        this.form = new FormGroup({
          name: new FormControl(t.name),
          capacity: new FormControl(t.capacity),
          latitude: new FormControl(t.latitude),
          longitude: new FormControl(t.longitude)
        })
      }
    )
  }

  saveChanges(user: string, form: FormGroup) {
    let airport = new AirportChange(
      form.getRawValue().name,
      form.getRawValue().capacity,
      form.getRawValue().latitude,
      form.getRawValue().longitude
    )
    this.airportService.editAirport(user, airport).subscribe()
    this.router.navigate(['/airportDetails/' + user])
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
