import { Component, OnInit } from '@angular/core';
import { Quack } from '../../quack'
import {AuthQuackService} from "../../auth/auth-quack.service";

@Component({
  selector: 'wt2-user-quack',
  templateUrl: './user-quack.component.html',
  styleUrls: ['./user-quack.component.sass']
})
export class UserQuackComponent implements OnInit {

   userQuacks?: Quack[];

   constructor(private authQuackService: AuthQuackService) {}

   ngOnInit(): void {
      this.getQuacksFromUser();
   }

   private getQuacksFromUser() {
       this.authQuackService.getQuacksFromUser().subscribe( data => {
         this.userQuacks = data;
         console.log(this.userQuacks);
       });
   }

}
