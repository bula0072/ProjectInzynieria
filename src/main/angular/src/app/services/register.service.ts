import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";

const usersApi = "http://localhost:8080/api/users/"


@Injectable({
  providedIn: 'root'
})
export class RegisterService {

  constructor(private http: HttpClient) {
  }

  register(newUser: UserNewDTO) {
    return this.http.post(
      usersApi,
      newUser,
      {responseType: "text"}
    )
  }
}

export class UserNewDTO {
  username: string
  password: string
  email: string

  constructor(username: string, password: string, email: string) {
    this.username = username;
    this.password = password;
    this.email = email;
  }
}
