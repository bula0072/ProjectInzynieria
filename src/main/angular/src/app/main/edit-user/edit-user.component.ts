import {Component, OnInit} from '@angular/core';
import {Observable} from "rxjs";
import {UserDTO} from "../../airport/airport-details/airport-details.component";
import {MainService} from "../services/main.service";
import {ActivatedRoute, ParamMap, Router} from "@angular/router";
import {switchMap} from "rxjs/operators";
import {FormControl, FormGroup} from "@angular/forms";
import {TokenService} from "../../services/token.service";

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrls: ['./edit-user.component.css']
})
export class EditUserComponent implements OnInit {
  form;
  details: Observable<UserDTO>;

  constructor(private mainService: MainService,
              private tokenService: TokenService,
              private route: ActivatedRoute,
              private router: Router) {
  }

  ngOnInit(): void {
    this.details = this.route.paramMap.pipe(
      switchMap((params: ParamMap) =>
        this.mainService.getUser(params.get('name')))
    )
    this.details.subscribe(t =>
      this.form = new FormGroup({
        username: new FormControl(t.username),
        password: new FormControl(''),
        email: new FormControl(t.email)
      }))


  }

  async saveChanges(username: string, form: FormGroup) {
    let user = new UserDTO(
      form.getRawValue().username,
      form.getRawValue().password,
      form.getRawValue().email
    )
    this.mainService.editUser(username, user).subscribe()
    let userToken = this.tokenService.getUser()
    userToken.sub = form.getRawValue().username == null ? username : form.getRawValue().username
    this.tokenService.saveUser(userToken)
    const delay = ms => new Promise(res => setTimeout(res, ms));
    await delay(100)
    await this.router.navigate(['/main/' + userToken.sub])
  }
}
