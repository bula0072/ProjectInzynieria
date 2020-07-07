import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {TicketNewDTO, TicketService} from "../services/ticket.service";

@Component({
  selector: 'app-new-ticket',
  templateUrl: './new-ticket.component.html',
  styleUrls: ['./new-ticket.component.css']
})
export class NewTicketComponent implements OnInit {
  seats: number;
  user: string
  flightId: number

  constructor(private route: ActivatedRoute,
              private ticketService: TicketService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.user = this.route.snapshot.paramMap.get('name')
    this.flightId = parseInt(this.route.snapshot.paramMap.get('flight'))
  }

  saveChanges() {
    console.log(this.seats + ' ' + this.user + ' ' + this.flightId)
    if (this.seats == null) return
    this.ticketService.newTicket(new TicketNewDTO(this.seats, this.user, this.flightId)).subscribe()
    this.router.navigate(['/flights'])
  }
}

