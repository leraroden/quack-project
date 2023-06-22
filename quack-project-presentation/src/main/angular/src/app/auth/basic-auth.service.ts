import { Injectable } from '@angular/core';
import {Observable, of,} from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AuthService } from './auth.service';
import { map } from 'rxjs/operators';
import { environment as env } from '../../environments/environment';

@Injectable()
export class BasicAuthService extends AuthService {

  private token: string;
  public errorMessage: string | null = null;

  constructor(protected override http: HttpClient) {
    super(http)
  }

  // TODO: vervollständige die Methode
  register(username: string, password: string): Observable<boolean> {
    return this.http.post(`${env.apiUrl}/users`, {'username' : username, "password" : password}, {observe: 'response'})
      .pipe(map(response => {
        if(response.status == 200){
          return true;
        }
        else {
          return false;
        }
      }));
  }


  override login(username: string, password: string): Observable<boolean> {
    const token = decodeURIComponent(encodeURIComponent(username + ':' + password));
    const encodedToken = window.btoa(token);

    return this.http.head(`${this.getBaseUrl()}/profile`, {headers: this.getAuthHeadersForToken(encodedToken), responseType: 'text'})
        .pipe(map(body => {
          this.setUsername(username);
          this.token = encodedToken;
          if(this.token){
            this.setToken(this.token);
            return true;
          }
          return false;
        }));
  }

  setUsername(username: string) {
    return localStorage.setItem('username', username);
  }

  getUsername(): string | null {
    return localStorage.getItem('username');
  }

  override logout(): Observable<boolean> {
    localStorage.removeItem('token');
    this.token = null;
    return of(true);
  }

  override getAuthHeaders(): HttpHeaders {
    this.token = this.getToken();
    return this.getAuthHeadersForToken(this.token);
  }

  override getBaseUrl(): string {
    return `${env.apiUrl}/auth/basic`;
  }

  override get isLoggedIn(): boolean {
    return !!(this.getToken());
  }

  override get isLoggedInAsAdmin(): boolean {
    return this.isLoggedIn && this.getUsername() === 'admin';
  }

  override get isLoggedInAsUser(): boolean {
    return this.isLoggedIn && !(this.getUsername() === 'admin');
  }

  getToken(): string | undefined{
    return localStorage.getItem('token') ?? undefined;
  }

  setToken(token: string): void {
    return localStorage.setItem('token', token);
  }

  removeToken(): void {
    return localStorage.removeItem('token');
  }

  getAuthHeadersForToken(token: string): HttpHeaders {
    return token == null ? new HttpHeaders() : new HttpHeaders({
      "Authorization": `Basic ${token}`
    });
  }
}
