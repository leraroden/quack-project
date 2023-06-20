import { Component, OnInit, Input } from '@angular/core';
import { Quack } from '../../quack'
import {AuthQuackService} from "../../auth/auth-quack.service";
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'wt2-user-quack',
  templateUrl: './user-quack.component.html',
  styleUrls: ['./user-quack.component.sass']
})
export class UserQuackComponent implements OnInit {

   @Input() userQuacks?: Quack[];

   constructor(
       private authQuackService: AuthQuackService,
       private route: ActivatedRoute,
       private router: Router) {}

   ngOnInit(): void {
      this.getQuacksFromUser();
   }

   getQuacksFromUser() {
       this.authQuackService.getQuacksFromUser().subscribe( data => {
         this.userQuacks = data;
         console.log(this.userQuacks);
       });
   }

}
