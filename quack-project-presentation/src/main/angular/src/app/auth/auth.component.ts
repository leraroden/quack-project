import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AuthNewsService } from './auth-news.service';
import { AngularComponent } from '../angular/angular.component';
import { BasicAuthService } from './basic-auth.service';
import { AuthService } from './auth.service';
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
          this.useBasicAuth();
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

  isBasicAuth(): boolean {
    return this.authService instanceof BasicAuthService;
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
