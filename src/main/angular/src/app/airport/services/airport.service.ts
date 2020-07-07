import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {AirportDTO} from "../airport-details/airport-details.component";
import {Observable} from "rxjs";
import {AirportChange} from "../edit-airport/edit-airport.component";

const airportApi = "http://localhost:8080/api/airports/"

@Injectable({
  providedIn: 'root'
})
export class AirportService {

  constructor(private http: HttpClient) {
  }

  getAllAirports(): Observable<Array<AirportDTO>> {
    return this.http.get<Array<AirportDTO>>(airportApi, {responseType: "json"})
  }

  getAirportDetails(name: string): Observable<AirportDTO> {
    return this.http.get<AirportDTO>(airportApi + 'user/' + name, {responseType: "json"})
  }

  getAirport(name: string): Observable<AirportDTO> {
    return this.http.get<AirportDTO>(airportApi + name, {responseType: "json"})
  }

  setActiveStatus(name: string) {
    return this.http.post(airportApi + 'set/' + name, {}, {responseType: "text"})
  }

  editAirport(name: string, body: AirportChange) {
    return this.http.patch(airportApi + name, body, {responseType: "text"})
  }

  deleteAirport(name: string) {
    return this.http.delete(airportApi + name, {responseType: "text"})
  }

  newAirport(newAirport: AirportNewDto) {
    return this.http.post(
      airportApi,
      newAirport,
      {responseType: "text"}
    )
  }
}

export class AirportNewDto {
  name: string
  capacity: number
  latitude: number
  longitude: number
  user: string

  constructor(name: string, capacity: number, latitude: number, longitude: number, user: string) {
    this.name = name;
    this.capacity = capacity;
    this.latitude = latitude;
    this.longitude = longitude;
    this.user = user;
  }
}
