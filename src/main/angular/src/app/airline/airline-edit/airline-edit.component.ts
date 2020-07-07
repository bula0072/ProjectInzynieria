import {Component, OnInit} from "@angular/core";
import {AirlineDTO, AirlinesService} from "../services/airlines.service";
import {Observable} from "rxjs";
import {ActivatedRoute, ParamMap, Router} from "@angular/router";
import {switchMap} from "rxjs/operators";

@Component({
  selector: 'app-airline-edit',
  templateUrl: './airline-edit.component.html',
  styleUrls: ['./airline-edit.component.css']
})
export class AirlineEditComponent implements OnInit {
  details: Observable<AirlineDTO>
  newName: string;

  constructor(private airlinesService: AirlinesService,
              private route: ActivatedRoute,
              private router: Router) {}

  async saveChanges(oldName: string, user: string) {
    if (this.newName == null) return
    this.airlinesService.editName(oldName, this.newName).subscribe()
    const delay = ms => new Promise(res => setTimeout(res, ms));
    await delay(500)
    await this.router.navigate(['/airlines/' + user])
  }

  ngOnInit(): void {
    this.details = this.route.paramMap.pipe(
      switchMap((p: ParamMap) =>
      this.airlinesService.getAirline(p.get('name')))
    )
  }
}
