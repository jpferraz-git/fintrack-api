import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { AuthLayout } from '../../components/auth-layout/auth-layout.component';
import { AuthCard } from '../../components/auth-card/auth-card.component';
import { FormInput } from '../../components/form-input/form-input.component';
import { CtaButton } from '../../components/cta-button/cta-button.component';
import { AuthService } from '../../app/services/auth.service';

@Component({
  selector: 'app-login-page',
  imports: [FormsModule, AuthLayout, AuthCard, FormInput, CtaButton, RouterLink],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginPage {

    email = ''
    password = ''
    loading = false
    errorMessage = ''

    constructor(
      private authService: AuthService,
      private router: Router
    ) {}

    login(): void {
      this.loading = true
      this.errorMessage = ''
      this.authService.login({
        email: this.email,
        password: this.password  
      }).subscribe ({
        next: () => {
          this.loading = false;
          this.router.navigate(['/dashboard']);
        },
        error: () => {
          this.loading = false;
          this.errorMessage = 'User or password incorrect'
        }
      })
    }
}