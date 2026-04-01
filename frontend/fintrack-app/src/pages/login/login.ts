import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { AuthLayout } from '../../components/auth-layout/auth-layout';
import { AuthCard } from '../../components/auth-card/auth-card';
import { FormInput } from '../../components/form-input/form-input';
import { CtaButton } from '../../components/cta-button/cta-button';

@Component({
  selector: 'app-login-page',
  imports: [AuthLayout, AuthCard, FormInput, CtaButton, RouterLink],
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class LoginPage {}