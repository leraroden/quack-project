import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Observable } from 'rxjs';
import {environment} from '../../environments/environment';

import { Quack } from '../quack';

@Injectable({
  providedIn: 'root'
})
export class QuackService {

  constructor(protected http: HttpClient) { }

  getAllQuacks(): Observable<Quack[]> {
    return this.http.get<Quack[]>(`${environment.apiUrl}/quacks/all`);
  }

  getAllQuacksSortedByDate(): Observable<Quack[]> {
    return this.http.get<Quack[]>(`${environment.apiUrl}/quacks/all/sortedByDate`);
  }

}
