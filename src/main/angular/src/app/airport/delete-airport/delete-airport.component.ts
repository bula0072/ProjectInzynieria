import {Component, OnInit} from '@angular/core';
import {AirportService} from "../services/airport.service";
import {ActivatedRoute, ParamMap, Router} from "@angular/router";
import {switchMap} from "rxjs/operators";
import {Observable} from "rxjs";
import {AirportDTO} from "../airport-details/airport-details.component";

@Component({
  selector: 'app-delete-airport',
  templateUrl: './delete-airport.component.html',
  styleUrls: ['./delete-airport.component.css']
})
export class DeleteAirportComponent implements OnInit {
  airport: Observable<AirportDTO>;

  constructor(private airportService: AirportService,
              private route: ActivatedRoute,
              private router: Router) {
  }

  ngOnInit(): void {
    this.airport = this.route.paramMap.pipe(
      switchMap((params: ParamMap) => this.airportService.getAirport(params.get('name')))
    )
  }

  delete(name: string) {
    this.airportService.deleteAirport(name).subscribe()
    this.router.navigate(['/'])
  }


}
