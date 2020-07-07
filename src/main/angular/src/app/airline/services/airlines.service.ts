import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {UserDTO} from "../../airport/airport-details/airport-details.component";
import {Observable} from "rxjs";

const airlineApi = 'http://localhost:8080/api/airlines/'

@Injectable({
  providedIn: 'root'
})
export class AirlinesService {
  constructor(private http: HttpClient) {
  }

  getAirlineByUser(username: string): Observable<AirlineDTO> {
    return this.http.get<AirlineDTO>(
      airlineApi + 'user/' + username,
      {responseType: "json"})
  }

  getAirline(airlineName: string): Observable<AirlineDTO> {
    return this.http.get<AirlineDTO>(
      airlineApi + airlineName,
      {responseType: "json"})
  }


  deleteAirline(name: string) {
    return this.http.delete(
      airlineApi + name,
      {responseType: "text"})
  }

  editName(oldName: string, newName: string) {
    return this.http.patch(
      airlineApi + oldName,
      {name: newName},
      {responseType: "text"})
  }

  addAirline(newName: string, username: string) {
    return this.http.post(
      airlineApi,
      {name: newName, user: username},
      {responseType: "text"})
  }

  getAllAirlines(): Observable<Array<AirlineDTO>> {
    return this.http.get<Array<AirlineDTO>>(
      airlineApi,
      {responseType: "json"}
    )
  }
}

export class AirlineDTO {
  name: string
  user: UserDTO
}
