import { Injectable } from '@angular/core';
import { Observable, of, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { AuthService } from './auth.service';
import { environment as env } from '../../environments/environment';

@Injectable()
export class SessionAuthService extends AuthService {
  
  private loggedIn: boolean = false;

  constructor(protected override http: HttpClient) {
    super(http)
  }

  override login(username: string, password: string): Observable<boolean> {
    const body = new HttpParams()
      .set('username', username)
      .set('password', password);

    const headers = new HttpHeaders({'Content-Type': 'application/x-www-form-urlencoded'});
    return this.http.post(`/login.jsp`, body.toString(), {headers, responseType: 'text'}).pipe(
      map(() => {
        this.loggedIn = true;
        return true;
      })
    );
  }

  override logout(): Observable<boolean> {
    return this.http.get(`/logout`).pipe(
      catchError(err => {
        return err.status == 0 ? of([]) : throwError(err);
      }),
      map(() => {
        this.loggedIn = false;
        return true;
      })
    );
  }

  override getAuthHeaders(): HttpHeaders {
    return new HttpHeaders();
  }

  override getBaseUrl(): string {
    return `${env.apiUrl}/auth/session`
  }

  override get isLoggedIn(): boolean {
    return this.loggedIn;
  }
}
