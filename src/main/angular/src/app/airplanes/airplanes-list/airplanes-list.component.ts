import {Component, OnInit} from '@angular/core';
import {AirplaneDTO, AirplaneService} from "../services/airplane.service";
import {Observable} from "rxjs";
import {ActivatedRoute, ParamMap} from "@angular/router";
import {switchMap} from "rxjs/operators";

@Component({
  selector: 'app-airplanes-list',
  templateUrl: './airplanes-list.component.html',
  styleUrls: ['./airplanes-list.component.css']
})
export class AirplanesListComponent implements OnInit {
  airplanes: Observable<Array<AirplaneDTO>>

  constructor(private route: ActivatedRoute,
              private airplaneService: AirplaneService) {
  }

  ngOnInit(): void {
    this.airplanes = this.route.paramMap.pipe(
      switchMap((p: ParamMap) =>
        this.airplaneService.getAirplanes(p.get('name')))
    )
  }

  removePlane(id: number) {
    this.airplaneService.removeAirplane(id).subscribe()
    window.location.reload()
  }
}
