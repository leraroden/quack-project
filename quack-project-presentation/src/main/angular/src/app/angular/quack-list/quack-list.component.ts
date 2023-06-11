import { Component, OnInit } from '@angular/core';
import { Quack } from '../quack'
import { QuackService } from '../quack.service';

@Component({
  selector: 'wt2-quack-list',
  templateUrl: './quack-list.component.html',
  styleUrls: ['./quack-list.component.sass']
})
export class QuackListComponent implements OnInit{
  quacks?: Quack[];

  constructor(private quackService: QuackService) {}

  ngOnInit(): void {
    this.getQuacks();
  }

  private getQuacks() {
    this.quackService.getAllQuacks().subscribe( data => {
      this.quacks = data;
      console.log(this.quacks);
      });
  }
}
