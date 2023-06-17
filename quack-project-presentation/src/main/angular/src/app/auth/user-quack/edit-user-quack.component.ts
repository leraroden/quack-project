import { Component, Input } from '@angular/core';
import { Quack } from '../../quack'
import {AuthQuackService} from "../../auth/auth-quack.service";

@Component({
  selector: 'wt2-edit-user-quack',
  templateUrl: './edit-user-quack.component.html',
  styleUrls: ['./edit-user-quack.component.sass']
})
export class EditQuackComponent {
   @Input() editQuack: Quack;

   editmode: boolean = false;
   editContent: string = '';

   constructor(private authQuackService: AuthQuackService) {}

   edit(quack: Quack) {
      this.editmode = true;
      this.editContent = quack.content;
   }

   save(quack: Quack) {
      this.editmode = false;
      quack.content = this.editContent;
      this.authQuackService.save(quack.id, quack);
      console.log(quack);
   }
}
