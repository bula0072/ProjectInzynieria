import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

const airplaneApi = 'http://localhost:8080/api/airplanes/'

@Injectable({
  providedIn: 'root'
})
export class AirplaneService {

  constructor(private http: HttpClient) {
  }

  getAirplanes(owner: String): Observable<Array<AirplaneDTO>> {
    return this.http.get<Array<AirplaneDTO>>(airplaneApi + 'user/' + owner, {responseType: "json"})
  }

  removeAirplane(id: number) {
    return this.http.delete(airplaneApi + id, {responseType: "text"})
  }

  addAirplane(airplaneAddDTO: AirplaneAddDTO) {
    return this.http.post(
      airplaneApi,
      airplaneAddDTO,
      {responseType: "text"})
  }
}

export class AirplaneDTO {
  id: number
  name: string
  capacity: number
  maxDistance: number
  user: string
}

export class AirplaneAddDTO {
  name: string
  capacity: number
  maxDistance: number
  user: string

  constructor(name: string, capacity: number, maxDistance: number, user: string) {
    this.name = name;
    this.capacity = capacity;
    this.maxDistance = maxDistance;
    this.user = user;
  }
}
