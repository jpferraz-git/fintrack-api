import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthLayout } from '../../components/auth-layout/auth-layout.component';
import { AuthCard } from '../../components/auth-card/auth-card.component';
import { FormInput } from '../../components/form-input/form-input.component';
import { CtaButton } from '../../components/cta-button/cta-button.component';
import { AuthService } from '../../app/services/auth.service';

@Component({
  selector: 'app-login-page',
  imports: [ReactiveFormsModule, AuthLayout, AuthCard, FormInput, CtaButton, RouterLink],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginPage {

    loginForm: FormGroup;
    loading = false;
    errorMessage = '';

    constructor(
      private fb: FormBuilder,
      private authService: AuthService,
      private router: Router
    ) {
        this.loginForm = this.fb.group({
            email: ['', [Validators.required, Validators.email]],
            password: ['', [Validators.required]]
        });
    }

    login(): void {
      if (this.loginForm.invalid) {
          return;
      }
      
      this.loading = true;
      this.errorMessage = '';
      
      const { email, password } = this.loginForm.value;
      
      this.authService.login({ email, password }).subscribe ({
        next: () => {
          this.loading = false;
          this.router.navigate(['/dashboard']);
        },
        error: () => {
          this.loading = false;
          this.errorMessage = 'User or password incorrect';
        }
      });
    }
}