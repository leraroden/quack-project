import {Component, Input, OnInit} from '@angular/core';
import { Quack } from '../../quack'
import { QuackService } from '../quack.service';

@Component({
  selector: 'wt2-quack-list',
  templateUrl: './quack-list.component.html',
  styleUrls: ['./quack-list.component.sass']
})
export class QuackListComponent{

  @Input()
  public quacks: Quack[] = [];

  constructor(private quackService: QuackService) {}
}
