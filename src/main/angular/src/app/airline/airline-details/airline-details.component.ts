import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, ParamMap} from "@angular/router";
import {switchMap} from "rxjs/operators";
import {Observable} from "rxjs";
import {AirlineDTO, AirlinesService} from "../services/airlines.service";

@Component({
  selector: 'app-airline-details',
  templateUrl: './airline-details.component.html',
  styleUrls: ['./airline-details.component.css']
})
export class AirlineDetailsComponent implements OnInit {
  details: Observable<AirlineDTO>;
  newName: string;
  username: string;

  constructor(private route: ActivatedRoute,
              private airlinesService: AirlinesService) {
  }

  ngOnInit(): void {
    this.username = this.route.snapshot.paramMap.get('name')
    this.details = this.route.paramMap.pipe(
      switchMap((params: ParamMap) =>
        this.airlinesService.getAirlineByUser(params.get('name'))))
  }

  saveChanges() {
    this.airlinesService.addAirline(this.newName, this.username).subscribe()
    window.location.reload()
  }
}
