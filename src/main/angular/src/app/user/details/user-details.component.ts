import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, ParamMap} from "@angular/router";
import {MainService} from "../../main/services/main.service";
import {switchMap} from "rxjs/operators";
import {Observable} from "rxjs";
import {UserDTO} from "../../airport/airport-details/airport-details.component";

@Component({
  selector: 'app-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.css']
})
export class UserDetailsComponent implements OnInit {
  userDetails: Observable<UserDTO>

  constructor(private mainService: MainService,
              private route: ActivatedRoute) { }

  ngOnInit(): void {
    console.log('tutaj jestem')
    this.userDetails = this.route.paramMap.pipe(
      switchMap((p: ParamMap) =>
      this.mainService.getUser(p.get('username')))
    )
  }

}
