import { Component, Input, Output, EventEmitter } from '@angular/core';
import { Quack } from '../../../quack'
import {AuthQuackService} from "../../../auth/auth-quack.service";
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'wt2-edit-user-quack',
  templateUrl: './edit-user-quack.component.html',
  styleUrls: ['./edit-user-quack.component.sass']
})
export class EditQuackComponent {
   @Input() editQuack: Quack;
   @Output() dataChanged: EventEmitter<void> = new EventEmitter<void>();

   editmode: boolean = false;
   editContent: string = '';

   constructor(
     private authQuackService: AuthQuackService,
     private route: ActivatedRoute,
     private router: Router) {}

   edit(quack: Quack) {
      this.editmode = true;
      this.editContent = quack.content;
   }

   save(quack: Quack) {
      this.editmode = false;
      quack.content = this.editContent;
      this.authQuackService.save(quack.id, quack).subscribe(() => {
        this.dataChanged.emit();
      });
   }

   delete(quack: Quack) {
      this.editmode = false;
      this.editContent = '';
      this.authQuackService.delete(quack.id).subscribe( data => {
        this.dataChanged.emit();
      });
      this.router.navigate(['/auth']);
   }
}
