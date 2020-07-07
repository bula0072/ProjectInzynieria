import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from "./public/login/login.component";
import {MainComponent} from "./main/main.component";
import {AirportDetailsComponent} from "./airport/airport-details/airport-details.component";
import {EditAirportComponent} from "./airport/edit-airport/edit-airport.component";
import {DeleteAirportComponent} from "./airport/delete-airport/delete-airport.component";
import {AirportFlightsComponent} from "./airport/airport-flights/airport-flights.component";
import {DeleteUserComponent} from "./main/delete-user/delete-user.component";
import {EditUserComponent} from "./main/edit-user/edit-user.component";
import {AirlineFlightsComponent} from "./airline/airline-flights/airline-flights.component";
import {AirplanesListComponent} from "./airplanes/airplanes-list/airplanes-list.component";
import {AirlineDetailsComponent} from "./airline/airline-details/airline-details.component";
import {NewFlightComponent} from "./flight/new-flight/new-flight.component";
import {FlightsComponent} from "./flight/flights/flights.component";
import {UserDetailsComponent} from "./user/details/user-details.component";
import {UserListComponent} from "./user/user-list/user-list.component";
import {DeleteUserByAdminComponent} from "./admin/delete-user-by-admin/delete-user-by-admin.component";
import {UserDetailsForAdminComponent} from "./admin/user-details-for-admin/user-details-for-admin.component";
import {AirlineEditComponent} from "./airline/airline-edit/airline-edit.component";
import {AirlineDeleteComponent} from "./airline/airline-delete/airline-delete.component";
import {AddAirplaneComponent} from "./airplanes/add-airplane/add-airplane.component";
import {NewTicketComponent} from "./tickets/new-ticket/new-ticket.component";
import {USerFlightsComponent} from "./user/user-flights/user-flights.component";
import {RegisterComponent} from "./public/register/register.component";


const routes: Routes = [
  {path: 'main/:name', component: MainComponent},
  {path: 'login', component: LoginComponent},
  {path: 'airportDetails/:name', component: AirportDetailsComponent},
  {path: 'airports/edit/:name', component: EditAirportComponent},
  {path: 'airports/delete/:name', component: DeleteAirportComponent},
  {path: 'airports/flights/:name', component: AirportFlightsComponent},
  {path: 'main/delete/:name', component: DeleteUserComponent},
  {path: 'main/edit/:name', component: EditUserComponent},
  {path: 'airlines/flights/:name', component: AirlineFlightsComponent},
  {path: 'airlines/:name', component: AirlineDetailsComponent},
  {path: 'airplanes/:name', component: AirplanesListComponent},
  {path: 'airlines/new/:name', component: NewFlightComponent},
  {path: 'flights', component: FlightsComponent},
  {path: 'user/:username', component: UserDetailsComponent},
  {path: 'users', component: UserListComponent},
  {path: 'users/delete/:name', component: DeleteUserByAdminComponent},
  {path: 'users/edit/:name', component: UserDetailsForAdminComponent},
  {path: 'airline/edit/:name', component: AirlineEditComponent},
  {path: 'airline/delete/:name', component: AirlineDeleteComponent},
  {path: 'airplane/add/:name', component: AddAirplaneComponent},
  {path: 'ticket/add/:name/:flight', component: NewTicketComponent},
  {path: 'users/flights', component: USerFlightsComponent},
  {path: 'register', component: RegisterComponent},
  {path: '', component: MainComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
