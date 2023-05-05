import { Injectable } from '@angular/core';
import { Observable, of, throwError } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AuthService } from './auth.service';
import { map } from 'rxjs/operators';
import { environment as env } from '../../environments/environment';

@Injectable()
export class BasicAuthService extends AuthService {

  private token: string;

  constructor(protected override http: HttpClient) {
    super(http)
  }

  override login(username: string, password: string): Observable<boolean> {
    const token = decodeURIComponent(encodeURIComponent(username + ':' + password));
    const encodedToken = window.btoa(token);

    return this.http.head(`${this.getBaseUrl()}/profile`, {headers: this.getAuthHeadersForToken(encodedToken), responseType: 'text'})
        .pipe(map(body => {
          this.token = encodedToken;
          return true;
        }));
  }

  override logout(): Observable<boolean> {
    this.token = null;
    return of(true);
  }

  override getAuthHeaders(): HttpHeaders {
    return this.getAuthHeadersForToken(this.token);
  }

  override getBaseUrl(): string {
    return `${env.apiUrl}/auth/basic`;
  }

  override get isLoggedIn(): boolean {
    return this.token != null;
  }

  getAuthHeadersForToken(token: string): HttpHeaders {
    return token == null ? new HttpHeaders() : new HttpHeaders({
      "Authorization": `Basic ${token}`
    });
  }
}
