import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

export abstract class AuthService {

  constructor(protected http: HttpClient) {
  }

  abstract register(username: string, password: string): Observable<boolean>;

  abstract login(username: string, password: string): Observable<boolean>;

  abstract logout(): Observable<boolean>;

  abstract getAuthHeaders(): HttpHeaders;

  abstract getBaseUrl(): string;

  abstract get isLoggedIn(): boolean;

  abstract getToken(): string | undefined;

  abstract setToken(token: string): void;

  abstract removeToken(): void;
}
