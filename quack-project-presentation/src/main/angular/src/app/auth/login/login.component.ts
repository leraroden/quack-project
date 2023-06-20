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

  register(e: Event) {
    e.preventDefault();
    this.errorMessage = null;
    if (this.username.trim() !== "" && this.password.trim() !== "") {
      this.authService.register(this.username, this.password).subscribe(
        () => {
          this.successMessage = "Registered";
        },
        (error) => {
          if (error.status === 409) {
            this.errorMessage = "Username already exists";
          } else if(error.status === 504){
            this.errorMessage = "Server not reachable";
          }else{
            this.errorMessage = "Failed to register";
          }
        }
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
