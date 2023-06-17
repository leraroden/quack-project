import {Injectable} from "@angular/core";
import {AuthService} from "./auth.service";
import {HttpClient} from "@angular/common/http";
import {BasicAuthService} from "./basic-auth.service";
import {Quack} from "../quack";
import {Observable} from "rxjs";
import {QuackService} from "../angular/quack.service";
import {map} from "rxjs/operators";

@Injectable()
export class AuthQuackService extends QuackService{

  private authService: AuthService;
  constructor(http: HttpClient) {
    super(http);
    this.authService = new BasicAuthService(http);
  }

  setAuthService(authService: AuthService) {
    this.authService = authService;
  }

  create(content: string): Observable<Quack> {
    return this.http.post<any>(`${this.authService.getBaseUrl()}/quacks`, {content}, {headers: this.authService.getAuthHeaders()}).pipe(
      map(body => Quack.fromObject(body))
    );
  }

  getQuacksFromUser(): Observable<Quack[]> {
    return this.http.get<any>(`${this.authService.getBaseUrl()}/quacks`, {headers: this.authService.getAuthHeaders()});
  }

  save(id: number, quack: Quack): Observable<Quack> {
    return this.http.put<Quack>(`${this.authService.getBaseUrl()}/quacks/${id}`, {quack}, {headers: this.authService.getAuthHeaders()});
  }


}
