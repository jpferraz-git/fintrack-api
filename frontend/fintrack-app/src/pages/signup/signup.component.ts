import { Component } from '@angular/core';
import { NgIf } from '@angular/common';
import { RouterLink } from '@angular/router';
import { AuthLayout } from '../../components/auth-layout/auth-layout.component';
import { AuthCard } from '../../components/auth-card/auth-card.component';
import { FormInput } from '../../components/form-input/form-input.component';
import { CtaButton } from '../../components/cta-button/cta-button.component';

@Component({
  selector: 'app-signup-page',
  imports: [NgIf, AuthLayout, AuthCard, FormInput, CtaButton, RouterLink],
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.css'
})
export class SignupPage {
  password = '';
  confirmPassword = '';
  showPasswordMismatchWarning = false;

  onSignupSubmit(event: Event): void {
    event.preventDefault();

    const passwordsMatch = this.password === this.confirmPassword;
    this.showPasswordMismatchWarning = !passwordsMatch;

    if (!passwordsMatch) {
      return;
    }

    // TODO: seguir com o fluxo de cadastro quando a integração estiver pronta.
  }
}