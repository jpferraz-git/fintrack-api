import { Component } from '@angular/core';
import { AuthLayout } from '../../components/auth-layout/auth-layout.component';
import { AuthCard } from '../../components/auth-card/auth-card.component';
import { FormInput } from '../../components/form-input/form-input.component';
import { CtaButton } from '../../components/cta-button/cta-button.component';
import { SignupService } from '../../app/services/signup.service';
import { Router, RouterLink } from '@angular/router';
import { UtilsService } from '../../app/services/utils.service';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-signup-page',
  imports: [ReactiveFormsModule, AuthLayout, AuthCard, FormInput, CtaButton, RouterLink],
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.css'
})
export class SignupPage {
  signupForm: FormGroup;
  showPassword = false;
  showConfirmPassword = false;
  passwordValidationError = '';
  showPasswordMismatchWarning = false;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private singupService: SignupService,
    private utilsService: UtilsService
  ) {
      this.signupForm = this.fb.group({
          name: ['', [Validators.required, Validators.minLength(2)]],
          email: ['', [Validators.required, Validators.email]],
          password: ['', [
              Validators.required,
              Validators.minLength(8),
              Validators.pattern("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}$")
          ]],
          confirmPassword: ['', [Validators.required]]
      });
  }

  onSignupSubmit(event: Event): void {
    event.preventDefault();
    if (this.signupForm.invalid) {
        this.signupForm.markAllAsTouched();
        return;
    }

    this.passwordValidationError = '';
    this.showPasswordMismatchWarning = false;

    const { name, email, password, confirmPassword } = this.signupForm.value;

    const passwordsMatch = password === confirmPassword;
    this.showPasswordMismatchWarning = !passwordsMatch;
    
    if (!passwordsMatch) {
      return
    }
    
    this.singupService.singUp({
      name,
      email,
      password,
    }).subscribe ({
      next: () => {
          this.router.navigate(['/login'])
      }
    })
  }

  togglePasswordVisibility(): void {
    this.showPassword = this.utilsService.toggleBoolean(this.showPassword);
  }

  toggleConfirmPasswordVisibility(): void {
    this.showConfirmPassword = this.utilsService.toggleBoolean(this.showConfirmPassword);
  }

}