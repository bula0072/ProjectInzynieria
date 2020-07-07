import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {AirportDTO} from "../../airport/airport-details/airport-details.component";
import {AirplaneDTO} from "../../airplanes/services/airplane.service";
import {Observable} from "rxjs";

const flightApi = "http://localhost:8080/api/flights/"

@Injectable({
  providedIn: 'root'
})
export class FlightService {

  constructor(private http: HttpClient) {
  }

  getAllFlights(): Observable<Array<FlightDTO>> {
    return this.http.get<Array<FlightDTO>>(flightApi)
  }

  getAllFlightsByAirport(airportName: string): Observable<Array<FlightDTO>> {
    return this.http.get<Array<FlightDTO>>(flightApi + 'airport/' + airportName)
  }

  getAllFlightsByAirline(airlineName: string): Observable<Array<FlightDTO>> {
    return this.http.get<Array<FlightDTO>>(flightApi + 'airline/' + airlineName)
  }

  addNewFlight(newFlight: FlightAddDTO) {
    return this.http.post(
      flightApi,
      newFlight,
      {responseType: "text"})
  }

  deleteFlight(id: number) {
    return this.http.delete(
      flightApi + id,
      {responseType: "text"}
    )
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

export class FlightAddDTO {
  cost: number
  startDate: string
  endDate: string
  startAirport: string
  endAirport: string
  airplane: number

  constructor(cost: number, startDate: string, endDate: string, startAirport: string, endAirport: string, airplane: number) {
    this.cost = cost;
    this.startDate = FlightAddDTO.dateCreator(startDate)
    this.endDate = FlightAddDTO.dateCreator(endDate)
    this.startAirport = startAirport;
    this.endAirport = endAirport;
    this.airplane = airplane;
  }

  static dateCreator(date: string) {
    let dateTable = date
      .replace('-', '.')
      .replace(':', '.')
      .split('.');
    return dateTable[2]
      + '-'
      + dateTable[1]
      + '-'
      + dateTable[0]
      + 'T'
      + dateTable[3]
      + ':'
      + dateTable[4]
      + ':00.00Z'
  }
}

// yyyy-mm-ddThh:mm:ss.00Z

// 11.07.2020-15:00
// 11.07.2020.15.00
// ['11','07','2020','15','00]
/////0////1/////2/////3/////4
