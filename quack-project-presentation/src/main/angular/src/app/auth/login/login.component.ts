import { Component, EventEmitter, Input, Output } from '@angular/core';
import { AuthService } from '../auth.service';

@Component({
  selector: 'wt2-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.sass']
})
export class LoginComponent {

  @Input()
  public authService: AuthService;

  @Output()
  public loggedIn = new EventEmitter<void>();

  public username: string = "";
  public password: string = "";
  public errorMessage: string;
  public successMessage: string;

  //TODO: diese Methode gibt immer "Failed to register" aus und muss gefixed werden
  register(e: Event) {
    e.preventDefault();
    this.errorMessage = null;
    if (this.username.trim() !== "" && this.password.trim() !== "") {
      this.authService.register(this.username, this.password).subscribe(
        () => this.successMessage = "Registered",
        () => this.errorMessage = "Failed to register"
      );
    }
  }

  login(e: Event) {
    e.preventDefault();
    this.errorMessage = null;
    if (this.canLogin) {
      this.authService.login(this.username, this.password).subscribe({
        next: () => this.loggedIn.emit(),
        error: () => this.errorMessage = 'Failed to login'
      });
    }
  }

  get canLogin(): boolean {
    return this.username.trim() !== '' && this.password.trim() !== '';
  }

  get canRegister():boolean{
    return this.username.trim() !== '' && this.password.trim() !== '';
  }
}
