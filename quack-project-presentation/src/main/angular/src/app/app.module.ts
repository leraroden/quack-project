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
import { AuthQuackService } from './auth/auth-quack.service';
import { UserQuackComponent } from './auth/user-quack/user-quack.component';
import { EditQuackComponent } from './auth/user-quack/edit-user-quack/edit-user-quack.component';
import { AdminQuackComponent } from './auth/user-quack/admin-quack.component';

@NgModule({
    declarations: [
        AppComponent,
        AngularComponent,
        AuthComponent,
        CreateQuackAuthComponent,
        LoginComponent,
        QuackListComponent,
        EnvironmentBadgeComponent,
        UserQuackComponent,
        EditQuackComponent,
        AdminQuackComponent
    ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        NgbModule,
        FormsModule,
        HttpClientModule
    ],
    providers: [AuthQuackService],
    exports: [
    ],
    bootstrap: [AppComponent]
})
export class AppModule { }
