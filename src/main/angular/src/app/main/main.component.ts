import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {Observable} from "rxjs";
import {UserDTO} from "../airport/airport-details/airport-details.component";
import {MainService} from "./services/main.service";
import {TokenService} from "../services/token.service";

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {
  details: Observable<UserDTO>;

  constructor(
    private mainService: MainService,
    private route: ActivatedRoute,
    private tokenService: TokenService,
    private router: Router
  ) {
  }

  ngOnInit(): void {
    try {
      this.details = this.mainService.getUser(this.tokenService.getUser().sub)
    } catch (e) {
      this.router.navigate(['/flights'])
    }
  }
}
