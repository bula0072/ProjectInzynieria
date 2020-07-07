import {Component, OnInit} from '@angular/core';
import {Observable} from "rxjs";
import {FlightService} from "../../flight/services/flight.service";
import {TokenService} from "../../services/token.service";
import {TicketDTO, TicketService} from "../../tickets/services/ticket.service";

@Component({
  selector: 'app-user-flights',
  templateUrl: './user-flights.component.html',
  styleUrls: ['./user-flights.component.css']
})
export class USerFlightsComponent implements OnInit {
  flights: Observable<Array<TicketDTO>>;
  private user: string;

  constructor(private tokenService: TokenService,
              private flightService: FlightService,
              private ticketService: TicketService) {
  }

  ngOnInit(): void {
    this.user = this.tokenService.getUser().sub
    this.flights = this.ticketService.getAllTicketsByUsername(this.user)
  }

  unsubscribe(id: number) {
    this.ticketService.removeTicket(id).subscribe()
    window.location.reload()
  }
}
