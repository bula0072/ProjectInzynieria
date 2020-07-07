import {Component, OnInit} from '@angular/core';
import {RegisterService, UserNewDTO} from "../../services/register.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  username: string;
  password: string;
  email: string;

  constructor(private registerService: RegisterService,
              private router: Router) {
  }

  ngOnInit(): void {

  }

  saveChanges() {
    if (this.username == '' || this.password == '' || this.email == '') {
      return
    }
    this.registerService.register(new UserNewDTO(
      this.username,
      this.password,
      this.email)).subscribe()
    this.router.navigate(['/login'])
  }
}
