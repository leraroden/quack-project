import {Component, EventEmitter, Output} from "@angular/core";
import {AuthQuackService} from "../auth-quack.service";

@Component({
  selector: 'wt2-create-quack-auth',
  templateUrl: './create-quack-auth.component.html',
  styleUrls: ['./create-quack-auth.component.sass']
})
export class CreateQuackAuthComponent {

    @Output()
  public created = new EventEmitter();

    public content: string = "";
    public errorMessage: string | null = null;
    public successMessage: string | null = null;
    constructor(private authQuackService: AuthQuackService) { }

  public createQuack(e: Event): void {
    e.preventDefault();
    this.errorMessage = null;
    this.successMessage = null;
    if (this.content.trim() != null) {
      this.authQuackService.create(this.content).subscribe({
        next: () => {
          this.created.emit();
          this.content = '';
          this.successMessage = 'New Quack added';
        },
        error: () => this.errorMessage = 'Could not create quack'
      });
    }
  }

  getCharsLeft(): number {
    return 255 - this.content.length;
  }

  get canCreate(): boolean {
    return this.getCharsLeft() > 0;
  }
}
