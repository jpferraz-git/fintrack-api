import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-settings-security-card',
  imports: [FormsModule],
  templateUrl: './settings-security-card.component.html',
  styleUrl: './settings-security-card.component.css'
})
export class SettingsSecurityCard {
  currentPassword = '';
  newPassword = '';
  confirmPassword = '';

  passwordUpdated = false;
  passwordError = '';

  updatePassword(): void {
    this.passwordError = '';
    this.passwordUpdated = false;

    if (this.newPassword.length < 12) {
      this.passwordError = 'New password must be at least 12 characters.';
      return;
    }

    if (this.newPassword !== this.confirmPassword) {
      this.passwordError = 'New password and confirmation do not match.';
      return;
    }

    this.currentPassword = '';
    this.newPassword = '';
    this.confirmPassword = '';
    this.passwordUpdated = true;

    setTimeout(() => {
      this.passwordUpdated = false;
    }, 2400);
  }
}
