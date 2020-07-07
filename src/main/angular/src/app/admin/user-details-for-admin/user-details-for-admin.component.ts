import {Component, OnInit} from '@angular/core';
import {Observable} from "rxjs";
import {AirportDTO, UserDTO} from "../../airport/airport-details/airport-details.component";
import {ActivatedRoute, Router} from "@angular/router";
import {MainService} from "../../main/services/main.service";
import {AirportService} from "../../airport/services/airport.service";
import {AirlineDTO, AirlinesService} from "../../airline/services/airlines.service";

@Component({
  selector: 'app-user-details-for-admin',
  templateUrl: './user-details-for-admin.component.html',
  styleUrls: ['./user-details-for-admin.component.css']
})
export class UserDetailsForAdminComponent implements OnInit {
  details: Observable<UserDTO>;
  airport: Observable<AirportDTO>;
  airline: Observable<AirlineDTO>;
  isEditable: boolean;
  role: string;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private mainService: MainService,
              private airportService: AirportService,
              private airlinesService: AirlinesService) {
  }

  ngOnInit(): void {
    let name = this.route.snapshot.paramMap.get('name')
    this.details = this.mainService.getUser(name)
    this.airport = this.airportService.getAirportDetails(name)
    this.airline = this.airlinesService.getAirlineByUser(name)
    this.isEditable = false
  }

  edit() {
    this.isEditable = !this.isEditable
  }

  async changeRole(oldRole: string,) {
    this.role = this.role.toUpperCase().replace(' ', '_')
    if (this.role == undefined
      || !['ADMIN', 'AIRLINE_OWNER', 'AIRPORT_OWNER', 'USER']
        .some(e => e == this.role)) return
    if (oldRole == 'AIRPORT_OWNER')
      this.airport.subscribe(t => this.airportService.deleteAirport(t.name))
    if (oldRole == 'AIRLINE_OWNER')
      this.airline.subscribe(t => this.airlinesService.deleteAirline(t.name))
    this.details.subscribe(t =>
      this.mainService.changeRole(this.role, t.username).subscribe())
    const delay = ms => new Promise(res => setTimeout(res, ms));
    await delay(100)
    await this.router.navigate(['/users'])
  }
}
