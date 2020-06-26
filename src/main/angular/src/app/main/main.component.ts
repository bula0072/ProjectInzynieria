import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, ParamMap} from "@angular/router";
import {switchMap} from "rxjs/operators";
import {Observable} from "rxjs";
import {AirportDTO, UserDTO} from "../airport/airport-details/airport-details.component";
import {MainService} from "./services/main.service";

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {
  details: Observable<UserDTO>;
  constructor(
    private mainService: MainService,
    private route: ActivatedRoute
  ) {
  }

  ngOnInit(): void {
    this.details = this.route.paramMap.pipe(
      switchMap((params: ParamMap) => this.mainService.getUser(params.get('name')))
    )
  }
}
