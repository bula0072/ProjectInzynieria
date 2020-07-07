import {Component, OnInit} from '@angular/core';
import {AirlineDTO, AirlinesService} from "../services/airlines.service";
import {ActivatedRoute, ParamMap, Router} from "@angular/router";
import {Observable} from "rxjs";
import {switchMap} from "rxjs/operators";

@Component({
  selector: 'app-airline-delete',
  templateUrl: './airline-delete.component.html',
  styleUrls: ['./airline-delete.component.css']
})
export class AirlineDeleteComponent implements OnInit {
  airline: Observable<AirlineDTO>

  constructor(private airlinesService: AirlinesService,
              private route: ActivatedRoute,
              private router: Router) {
  }

  ngOnInit(): void {
    this.airline = this.route.paramMap.pipe(
      switchMap((p: ParamMap) =>
        this.airlinesService.getAirline(p.get('name')))
    )
  }

  delete(name: string, username: string) {
    this.airlinesService.deleteAirline(name).subscribe()
    this.router.navigate(['/airlines', username])
  }

}
