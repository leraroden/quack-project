import { Component, OnInit } from '@angular/core';
import {QuackService} from "./quack.service";
import {Quack} from "../quack";

@Component({
  selector: 'wt2-angular',
  templateUrl: './angular.component.html',
  styleUrls: ['./angular.component.sass'],
  providers: [QuackService]
})
export class AngularComponent implements OnInit {

  public quacks: Quack[] = [];

  constructor(protected quackService: QuackService) {
  }

  ngOnInit() {
    this.load();
  }

  load(): void {
    this.quackService.getAllQuacks().subscribe({
      next: quacks => this.quacks = quacks,
      error: console.error
    });
  }
}
