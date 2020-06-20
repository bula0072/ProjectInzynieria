import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {LoginComponent} from './public/login/login.component';
import {ReactiveFormsModule} from "@angular/forms";
import {HttpLoginService} from "./services/http-login.service";
import {HttpClientModule} from "@angular/common/http";

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [HttpLoginService,
    HttpClientModule],
  bootstrap: [AppComponent]
})
export class AppModule {
}
