import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AngularComponent } from './angular/angular.component';
import { AuthComponent } from './auth/auth.component';
import { LoginComponent } from './auth/login/login.component';
import { EnvironmentBadgeComponent } from './shared/environment-badge/environment-badge.component';
import { QuackListComponent } from './angular/quack-list/quack-list.component';
import {CreateQuackAuthComponent} from "./auth/create-quack-auth/create-quack-auth.component";

@NgModule({
    declarations: [
        AppComponent,
        AngularComponent,
        AuthComponent,
        CreateQuackAuthComponent,
        LoginComponent,
        QuackListComponent,
        EnvironmentBadgeComponent
    ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        NgbModule,
        FormsModule,
        HttpClientModule
    ],
    providers: [],
    exports: [
    ],
    bootstrap: [AppComponent]
})
export class AppModule { }
