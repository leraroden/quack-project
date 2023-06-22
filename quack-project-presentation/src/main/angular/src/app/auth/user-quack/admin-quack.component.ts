import { Component, OnInit, Input } from '@angular/core';
import { Quack } from '../../quack'
import {AuthQuackService} from "../../auth/auth-quack.service";
import {QuackService} from "../../angular/quack.service";
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'wt2-admin-quack',
  templateUrl: './admin-quack.component.html'
})
export class AdminQuackComponent implements OnInit {

   @Input() adminQuacks?: Quack[];

   constructor(
       private authQuackService: AuthQuackService,
       private route: ActivatedRoute,
       private router: Router,
       private quackService: QuackService) {}

   ngOnInit(): void {
      this.getQuacksFromAdmin();
   }

   getQuacksFromAdmin() {
      /* this.quackService.getAllQuacks().subscribe( quacks => {
        this.adminQuacks = quacks;
      }); */
     console.log("Admin component quacks: " + this.adminQuacks);
   }

}
