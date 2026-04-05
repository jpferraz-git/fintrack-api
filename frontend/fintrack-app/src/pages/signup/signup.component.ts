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
  showPasswordMismatchWarning = false;

  constructor(
    private router: Router,
    private singupService: SignupService,
  ) {}

  onSignupSubmit(event: Event): void {
    event.preventDefault();
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

}