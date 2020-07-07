import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {LoginComponent} from './public/login/login.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import {AuthorizationService} from "./services/authorization.service";
import {AuthInterceptor} from "./AuthInterceptor";
import { NavigationBarComponent } from './navigation-bar/navigation-bar.component';
import { MainComponent } from './main/main.component';
import { UsersBarComponent } from './navigation-bar/users-bar/users-bar.component';
import { NormalUserComponent } from './navigation-bar/users-bar/normal-user/normal-user.component';
import { AdminComponent } from './navigation-bar/users-bar/admin/admin.component';
import { AirportOwnerComponent } from './navigation-bar/users-bar/airport-owner/airport-owner.component';
import { AirlineOwnerComponent } from './navigation-bar/users-bar/airline-owner/airline-owner.component';
import { GuestBarComponent } from './navigation-bar/guest-bar/guest-bar.component';
import { AirportDetailsComponent } from './airport/airport-details/airport-details.component';
import { EditAirportComponent } from './airport/edit-airport/edit-airport.component';
import { DeleteAirportComponent } from './airport/delete-airport/delete-airport.component';
import { AirportFlightsComponent } from './airport/airport-flights/airport-flights.component';
import { DeleteUserComponent } from './main/delete-user/delete-user.component';
import { EditUserComponent } from './main/edit-user/edit-user.component';
import { AirlineFlightsComponent } from './airline/airline-flights/airline-flights.component';
import { AirplanesListComponent } from './airplanes/airplanes-list/airplanes-list.component';
import { AirlineDetailsComponent } from './airline/airline-details/airline-details.component';
import { NewFlightComponent } from './flight/new-flight/new-flight.component';
import { FlightsComponent } from './flight/flights/flights.component';
import { UserDetailsComponent } from './user/details/user-details.component';
import { UserListComponent } from './user/user-list/user-list.component';
import { DeleteUserByAdminComponent } from './admin/delete-user-by-admin/delete-user-by-admin.component';
import { UserDetailsForAdminComponent } from './admin/user-details-for-admin/user-details-for-admin.component';
import { AirlineEditComponent } from './airline/airline-edit/airline-edit.component';
import { AirlineDeleteComponent } from './airline/airline-delete/airline-delete.component';
import { AddAirplaneComponent } from './airplanes/add-airplane/add-airplane.component';
import { USerFlightsComponent } from './user/user-flights/user-flights.component';
import { NewTicketComponent } from './tickets/new-ticket/new-ticket.component';
import { RegisterComponent } from './public/register/register.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    NavigationBarComponent,
    MainComponent,
    UsersBarComponent,
    NormalUserComponent,
    AdminComponent,
    AirportOwnerComponent,
    AirlineOwnerComponent,
    GuestBarComponent,
    AirportDetailsComponent,
    EditAirportComponent,
    DeleteAirportComponent,
    AirportFlightsComponent,
    DeleteUserComponent,
    EditUserComponent,
    AirlineFlightsComponent,
    AirplanesListComponent,
    AirlineDetailsComponent,
    NewFlightComponent,
    FlightsComponent,
    UserDetailsComponent,
    UserListComponent,
    DeleteUserByAdminComponent,
    UserDetailsForAdminComponent,
    AirlineEditComponent,
    AirlineDeleteComponent,
    AddAirplaneComponent,
    USerFlightsComponent,
    NewTicketComponent,
    RegisterComponent,

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [
    AuthorizationService,
    HttpClientModule,
  AuthInterceptor],
  bootstrap: [AppComponent]
})
export class AppModule {
}
