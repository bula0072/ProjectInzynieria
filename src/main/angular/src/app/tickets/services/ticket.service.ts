import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {UserDTO} from "../../airport/airport-details/airport-details.component";
import {FlightDTO} from "../../flight/services/flight.service";
import {Observable} from "rxjs";

const flightApi = "http://localhost:8080/api/tickets/"

@Injectable({
  providedIn: 'root'
})
export class TicketService {

  constructor(private http: HttpClient) {
  }

  newTicket(ticket: TicketNewDTO) {
    return this.http.post(
      flightApi,
      ticket,
      {responseType: "text"}
    )
  }

  getAllTicketsByUsername(username: string): Observable<Array<TicketDTO>> {
    console.log(username)
    return this.http.get<Array<TicketDTO>>(
      flightApi + 'user/' + username,
      {responseType: "json"}
    )
  }

  removeTicket(id: number) {
    return this.http.delete(flightApi + id,
      {responseType: "text"}
    )
  }
}

export class TicketNewDTO {
  quantity: number
  user: string
  flight: number

  constructor(quantity: number, user: string, flight: number) {
    this.quantity = quantity;
    this.user = user;
    this.flight = flight;
  }
}

export class TicketDTO {
  id: number
  quantity: number
  user: UserDTO
  flight: FlightDTO
}
