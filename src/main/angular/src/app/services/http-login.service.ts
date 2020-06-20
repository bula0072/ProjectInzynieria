import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {tap} from "rxjs/operators";

export class User {
  login: string;
  password: string;

  constructor(login: string, password: string) {
    this.login = login;
    this.password = password;
  }
}

@Injectable({
  providedIn: 'root'
})
export class HttpLoginService {
  private url = 'http://localhost:8080/api/public'
  private url2 = 'http://localhost:8080/api/admins'

  constructor(private http: HttpClient) {
  }

  get(): Observable<any> {
    const headers = new HttpHeaders().set('Content-Type', 'text/plain; charset=utf-8')
    return this.http.get(this.url + "/test", {headers, responseType: 'text'}).pipe(tap(console.log))
  }

  postLogin(/*user: User*/) {
    return this.http.post(this.url + '/login', 'test'/*, user*/)
  }
}
