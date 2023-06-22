import { Component, OnInit } from '@angular/core';
import { Quack } from '../quack'
import { HttpClient } from '@angular/common/http';
import { AngularComponent } from '../angular/angular.component';
import { BasicAuthService } from './basic-auth.service';
import { AuthService } from './auth.service';
import {ActivatedRoute, Router} from "@angular/router";
import {AuthQuackService} from "./auth-quack.service";

@Component({
  selector: 'wt2-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.sass'],
  providers: [AuthQuackService]
})
export class AuthComponent extends AngularComponent implements OnInit {

  private static readonly AUTH_METHOD_PARAM_NAME = 'method';

  authService: AuthService;
  userQuacks?: Quack[];
  username: string = '';

  constructor(private http: HttpClient,
              private authQuackService: AuthQuackService,
              private router: Router,
              private route: ActivatedRoute) {
    super(authQuackService);
    this.authService = new BasicAuthService(http);
    this.authQuackService.setAuthService(this.authService);
  }

  override ngOnInit() {
    this.route.queryParamMap.subscribe({
      next: () => {
        this.useBasicAuth();
      }
    });
  }

  logout() {
    this.authService.logout().subscribe();
    this.quacks = [];
    this.username = '';
  }

  useBasicAuth(e?: Event) {
    if (e != null) e.preventDefault();
    this.authService = new BasicAuthService(this.http);
    this.authQuackService.setAuthService(this.authService);
    this.reloadQueryParameters('basic');
  }

  private reloadQueryParameters(method: string): void {
    this.router.navigate(
      [],
      {
        relativeTo: this.route,
        queryParams: {
          [AuthComponent.AUTH_METHOD_PARAM_NAME]: method
        },
        queryParamsHandling: 'merge'
      });
  }

  get isLoggedIn(): boolean {
    this.username = this.authService.getUsername();
    return this.authService.isLoggedIn;
  }

  override load() {
    // Lade die Quacks, wenn der Benutzer angemeldet ist
    if (this.isLoggedIn) {
      this.authQuackService.getQuacksFromUser().subscribe(quacks => {
        this.userQuacks = quacks;
      });
    }
  }

  handleQuackCreated() {
    this.load();
  }

}
