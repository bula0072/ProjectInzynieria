import {Component, OnInit} from '@angular/core';
import {Observable} from "rxjs";
import {UserDTO} from "../../airport/airport-details/airport-details.component";
import {ActivatedRoute, ParamMap, Router} from "@angular/router";
import {MainService} from "../../main/services/main.service";
import {switchMap} from "rxjs/operators";

@Component({
  selector: 'app-delete-user-by-admin',
  templateUrl: './delete-user-by-admin.component.html',
  styleUrls: ['./delete-user-by-admin.component.css']
})
export class DeleteUserByAdminComponent implements OnInit {
  userDetail: Observable<UserDTO>

  constructor(private route: ActivatedRoute,
              private mainService: MainService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.userDetail = this.route.paramMap.pipe(
      switchMap((p: ParamMap) =>
        this.mainService.getUser(p.get('name')))
    )
  }

  async delete(username: string){
    this.mainService.deleteUser(username).subscribe()
    const delay = ms => new Promise(res => setTimeout(res, ms));
    await delay(1000)
    await this.router.navigate(['/users'])
  }
}
