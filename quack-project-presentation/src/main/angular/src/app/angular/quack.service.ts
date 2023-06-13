import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Observable } from 'rxjs';
import {environment as env, environment} from '../../environments/environment';

import { Quack } from './quack';
import {News} from "../news";
import {map} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class QuackService {

  constructor(private http: HttpClient) { }

  getAllQuacks(): Observable<Quack[]> {
    return this.http.get<Quack[]>(`${environment.apiUrl}/quacks/all`);
  }

  //TODO: muss implementiert werden
  create(content: string): Observable<Quack> {
    return undefined;
  }

}
