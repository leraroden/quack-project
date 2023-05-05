import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AuthService } from './auth.service';
import { environment as env } from '../../environments/environment';

@Injectable()
export class JwtAuthService extends AuthService {

  private token: string;

  constructor(protected override http: HttpClient) {
    super(http)
  }

  override login(username: string, password: string): Observable<boolean> {
    return this.http.post(`${this.getBaseUrl()}/authenticate`, {username, password}, {responseType: 'text'})
      .pipe(map(body => {
        this.token = body;
        return true;
      }));
  }

  override logout(): Observable<boolean> {
    this.token = null;
    return of(true);
  }

  override getAuthHeaders(): HttpHeaders {
    return this.token == null ? new HttpHeaders() : new HttpHeaders({
      "Authorization": `Bearer ${this.token}`
    });
  }

  override getBaseUrl(): string {
    return `${env.apiUrl}/auth/jwt`;
  }

  override get isLoggedIn(): boolean {
    return this.token != null;
  }
}
