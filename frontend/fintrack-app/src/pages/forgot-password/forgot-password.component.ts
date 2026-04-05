import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { AuthLayout } from '../../components/auth-layout/auth-layout.component';
import { AuthCard } from '../../components/auth-card/auth-card.component';
import { FormInput } from '../../components/form-input/form-input.component';
import { CtaButton } from '../../components/cta-button/cta-button.component';

@Component({
  selector: 'app-forgot-password-page',
  imports: [FormsModule, AuthLayout, AuthCard, FormInput, CtaButton, RouterLink],
  templateUrl: './forgot-password.component.html',
  styleUrl: './forgot-password.component.css'
})
export class ForgotPasswordPage {
  email = '';
  isSubmitting = false;
  emailSent = false;
  formError = '';

  requestResetEmail(): void {
    const normalizedEmail = this.email.trim().toLowerCase();

    this.formError = '';
    this.emailSent = false;

    if (!normalizedEmail) {
      this.formError = 'Please enter your email address.';
      return;
    }

    if (!this.isValidEmail(normalizedEmail)) {
      this.formError = 'Please enter a valid email address.';
      return;
    }

    this.isSubmitting = true;

    // Simulate the request flow until backend reset endpoint is available.
    setTimeout(() => {
      this.isSubmitting = false;
      this.emailSent = true;
      this.email = normalizedEmail;
    }, 700);
  }

  private isValidEmail(value: string): boolean {
    return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(value);
  }
}
