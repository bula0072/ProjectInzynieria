import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, ParamMap, Router} from "@angular/router";
import {MainService} from "../services/main.service";
import {switchMap} from "rxjs/operators";
import {Observable} from "rxjs";
import {UserDTO} from "../../airport/airport-details/airport-details.component";
import {TokenService} from "../../services/token.service";

@Component({
  selector: 'app-delete-user',
  templateUrl: './delete-user.component.html',
  styleUrls: ['./delete-user.component.css']
})
export class DeleteUserComponent implements OnInit {
  userDetail: Observable<UserDTO>;

  constructor(private route: ActivatedRoute,
              private mainService: MainService,
              private tokenService: TokenService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.userDetail = this.route.paramMap.pipe(
      switchMap((params: ParamMap) =>
        this.mainService.getUser(params.get('name')))
    )
  }

  delete(username: string) {
    this.mainService.deleteUser(username).subscribe()
    this.tokenService.signOut()
    this.router.navigate(['/'])
  }
}
