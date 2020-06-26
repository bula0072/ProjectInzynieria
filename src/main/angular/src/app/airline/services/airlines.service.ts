import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
@Injectable({
  providedIn: 'root'
})
export class AirlinesService {

  constructor(private http: HttpClient) { }

  getAirlineByUser(username: string){
    return 'airlineasdf'
  }
}
