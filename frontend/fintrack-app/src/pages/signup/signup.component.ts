import { Component } from '@angular/core';
import { AuthLayout } from '../../components/auth-layout/auth-layout.component';
import { AuthCard } from '../../components/auth-card/auth-card.component';
import { FormInput } from '../../components/form-input/form-input.component';
import { CtaButton } from '../../components/cta-button/cta-button.component';
import { SignupService } from '../../app/services/signup.service';
import { Router, RouterLink } from '@angular/router';


@Component({
  selector: 'app-signup-page',
  imports: [AuthLayout, AuthCard, FormInput, CtaButton, RouterLink],
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.css'
})


export class SignupPage {
  name = '';
  email = '';
  password = '';
  confirmPassword = '';
  showPassword = false;
  showConfirmPassword = false;
  passwordValidationError = '';
  showPasswordMismatchWarning = false;

  constructor(
    private router: Router,
    private singupService: SignupService,
  ) {}

  onSignupSubmit(event: Event): void {
    event.preventDefault();
    this.passwordValidationError = '';
    this.showPasswordMismatchWarning = false;

    const passwordValidationError = this.getPasswordValidationError(this.password);
    if (passwordValidationError) {
      this.passwordValidationError = passwordValidationError;
      return;
    }

    const passwordsMatch = this.password === this.confirmPassword;
    this.showPasswordMismatchWarning = !passwordsMatch;
    
    if (!passwordsMatch) {
      return
    }
    this.singupService.singUp({
      name: this.name,
      email: this.email,
      password: this.password,
    }).subscribe ({
      next: () => {
          this.router.navigate(['/login'])
      }
    })

  }

  togglePasswordVisibility(): void {
    this.showPassword = !this.showPassword;
  }

  toggleConfirmPasswordVisibility(): void {
    this.showConfirmPassword = !this.showConfirmPassword;
  }

  private getPasswordValidationError(password: string): string {
    if (password.length < 8) {
      return 'Password must be at least 8 characters long.';
    }

    if (!/[A-Z]/.test(password)) {
      return 'Password must include at least one uppercase letter.';
    }

    if (!/[a-z]/.test(password)) {
      return 'Password must include at least one lowercase letter.';
    }

    if (!/\d/.test(password)) {
      return 'Password must include at least one number.';
    }

    if (!/[^A-Za-z0-9]/.test(password)) {
      return 'Password must include at least one special character.';
    }

    return '';
  }

}