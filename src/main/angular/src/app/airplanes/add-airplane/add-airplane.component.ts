import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, ParamMap, Router} from "@angular/router";
import {switchMap} from "rxjs/operators";
import {AirlineDTO, AirlinesService} from "../../airline/services/airlines.service";
import {Observable} from "rxjs";
import {AirplaneAddDTO, AirplaneService} from "../services/airplane.service";

@Component({
  selector: 'app-add-airplane',
  templateUrl: './add-airplane.component.html',
  styleUrls: ['./add-airplane.component.css']
})
export class AddAirplaneComponent implements OnInit {
  details: Observable<AirlineDTO>;
  name: string;
  capacity: number;
  maxDistance: number;

  constructor(private route: ActivatedRoute,
              private airlinesService: AirlinesService,
              private airplaneService: AirplaneService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.details = this.route.paramMap.pipe(
      switchMap((s: ParamMap) =>
        this.airlinesService.getAirlineByUser(s.get('name')))
    )
  }

  async saveChanges(username: string) {
    console.log('before if')
    if (this.name == ''
      || this.capacity <= 0
      || this.maxDistance <= 0) {
      return
    }
    this.airplaneService.addAirplane(
      new AirplaneAddDTO(
        this.name,
        this.capacity,
        this.maxDistance,
        username)
    ).subscribe()
    const delay = ms => new Promise(res => setTimeout(res, ms));
    await delay(100)
    await this.router.navigate(['/airplanes/' + username])
  }
}
