import { Component, OnInit, Input } from '@angular/core';
import { Quack } from '../../quack';
import {AuthQuackService} from "../../auth/auth-quack.service";

@Component({
  selector: 'wt2-admin-quack',
  templateUrl: './admin-quack.component.html',
  styleUrls: ['./admin-quack.component.sass']
})
export class AdminQuackComponent implements OnInit {

   @Input() adminQuacks?: Quack[];

   constructor(private authQuackService: AuthQuackService) {}

   ngOnInit(): void {
      this.getQuacksFromAdmin();
   }

   getQuacksFromAdmin() {
      this.authQuackService.getAllQuacks().subscribe( quacks => {
        this.adminQuacks = quacks;
      });
   }

}
