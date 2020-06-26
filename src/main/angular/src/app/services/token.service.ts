import { Injectable } from '@angular/core';
import {UserToken} from "../public/login/login.component";
import {Observable} from "rxjs";

const tokenKey = 'auth-token';
const userKey = 'auth-user';

@Injectable({
  providedIn: 'root'
})
export class TokenService {

  constructor() { }

  signOut(){
    window.sessionStorage.clear()
  }

  saveToken(token: string){
    window.sessionStorage.removeItem(tokenKey)
    window.sessionStorage.setItem(tokenKey, token)
  }

  getToken(): string{
    return sessionStorage.getItem(tokenKey)
  }

  saveUser(user: UserToken){
    window.sessionStorage.removeItem(userKey)
    window.sessionStorage.setItem(userKey, JSON.stringify(user))
  }

  getUser(): UserToken{
    return JSON.parse(sessionStorage.getItem(userKey))
  }
}

