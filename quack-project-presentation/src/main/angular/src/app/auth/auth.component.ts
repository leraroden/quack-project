import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AuthNewsService } from './auth-news.service';
import { AngularComponent } from '../angular/angular.component';
import { JwtAuthService } from './jwt-auth.service';
import { BasicAuthService } from './basic-auth.service';
import { AuthService } from './auth.service';
import { SessionAuthService } from './session-auth.service';
import {ActivatedRoute, Params, Route, Router} from "@angular/router";

@Component({
  selector: 'wt2-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.sass'],
  providers: [AuthNewsService]
})
export class AuthComponent extends AngularComponent implements OnInit {

  private static readonly AUTH_METHOD_PARAM_NAME = 'method';

  authService: AuthService;

  constructor(private http: HttpClient,
              private authNewsService: AuthNewsService,
              private router: Router,
              private route: ActivatedRoute) {
    super(authNewsService);
  }

  override ngOnInit() {
    this.route.queryParamMap.subscribe({
      next: queryParams => {
        if (queryParams.has(AuthComponent.AUTH_METHOD_PARAM_NAME)) {
          switch (queryParams.get(AuthComponent.AUTH_METHOD_PARAM_NAME)) {
            case 'jwt':
              this.useJwtAuth();
              break;
            case 'session':
              this.useSessionAuth();
              break;
            default:
              this.useBasicAuth();
          }
        } else {
          this.useBasicAuth();
        }
      }
    });
  }

  logout() {
    this.authService.logout().subscribe();
    this.news = [];
    this.latest = null;
  }

  useBasicAuth(e?: Event) {
    if (e != null) e.preventDefault();
    this.authService = new BasicAuthService(this.http);
    this.authNewsService.authService = this.authService;
    this.reloadQueryParameters('basic');
  }

  useJwtAuth(e?: Event) {
    if (e != null) e.preventDefault();
    this.authService = new JwtAuthService(this.http);
    this.authNewsService.authService = this.authService;
    this.reloadQueryParameters('jwt');
  }

  useSessionAuth(e?: Event) {
    if (e != null) e.preventDefault();
    this.authService = new SessionAuthService(this.http);
    this.authNewsService.authService = this.authService;
    this.reloadQueryParameters('session');
  }

  isBasicAuth(): boolean {
    return this.authService instanceof BasicAuthService;
  }

  isJwtAuth(): boolean {
    return this.authService instanceof JwtAuthService;
  }

  isSessionAuth(): boolean {
    return this.authService instanceof SessionAuthService;
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
    return this.authService.isLoggedIn;
  }
}
