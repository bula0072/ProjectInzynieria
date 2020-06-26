import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class AuthorizationService {

  constructor(private http: HttpClient) {
  }

  login(user) {
    return this.http
      .post('http://localhost:8080/login', user, {observe: 'response'})
  }

  regiser(user) {
    return this.http.post(
      'http://localhost:8080/api/public/register',
      user,
      {observe: 'response'}
    )
  }
}
