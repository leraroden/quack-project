import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AngularComponent } from './angular/angular.component';
import { AuthComponent } from './auth/auth.component';

const routes: Routes = [
  { path: 'angular', component: AngularComponent },
  { path: 'auth', component: AuthComponent},
  {
    path: '',
    redirectTo: '/angular',
    pathMatch: 'full'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
