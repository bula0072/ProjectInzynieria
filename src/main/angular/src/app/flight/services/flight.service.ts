import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {AirportDTO} from "../../airport/airport-details/airport-details.component";
import {AirplaneDTO} from "../../airplanes/services/airplane.service";
import {Observable} from "rxjs";

const airportApi = "http://localhost:8080/api/flights/"

@Injectable({
  providedIn: 'root'
})
export class FlightService {

  constructor(private http: HttpClient) {
  }

  getAllFlights() : Observable<Array<FlightDTO>>{
    return this.http.get<Array<FlightDTO>>(airportApi)
  }
}

export class FlightDTO {
  id: number
  cost: number
  startDate: string
  endDate: string
  startAirport: AirportDTO
  endAirport: AirportDTO
  airplane: AirplaneDTO
}
