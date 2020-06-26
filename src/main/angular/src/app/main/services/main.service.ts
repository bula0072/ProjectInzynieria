import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {UserDTO} from "../../airport/airport-details/airport-details.component";
import {Observable} from "rxjs";

const userApi = "http://localhost:8080/api/users/"

@Injectable({
  providedIn: 'root'
})
export class MainService {

  constructor(private http: HttpClient) {
  }

  getUser(name: string): Observable<UserDTO>{
    return this.http.get<UserDTO>(userApi + name, {responseType: "json"})
  }

  deleteUser(username: string) {
    return this.http.delete(userApi + username, {responseType: "text"})
  }

  editUser(username: string, user: UserDTO) {
    return this.http.patch(userApi + username, user, {responseType: "text"})
  }
}
