import {Component, Input, OnInit} from '@angular/core';
import { Quack } from '../../quack'
import { QuackService } from '../quack.service';

@Component({
  selector: 'wt2-quack-list',
  templateUrl: './quack-list.component.html',
  styleUrls: ['./quack-list.component.sass']
})
export class QuackListComponent implements OnInit{

  @Input()
  public quacks: Quack[] = [];

  constructor(private quackService: QuackService) {}

  ngOnInit(): void {
    this.getQuacks();
  }

  private getQuacks() {
    this.quackService.getAllQuacksSortedByDate().subscribe( data => {
      this.quacks = data;
      });
  }
}
