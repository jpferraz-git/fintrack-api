import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { UserService } from '../../../app/services/user.service';

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
  isSubmitting = false;

  constructor(private userService: UserService) {}

  updatePassword(): void {
    this.passwordError = '';
    this.passwordUpdated = false;

    if (!this.currentPassword.trim()) {
      this.passwordError = 'Current password is required.';
      return;
    }

    if (this.newPassword !== this.confirmPassword) {
      this.passwordError = 'New password and confirmation do not match.';
      return;
    }

    const storedUser = localStorage.getItem('user');
    if (!storedUser) {
      this.passwordError = 'Unable to identify logged user.';
      return;
    }

    let currentUserEmail = '';

    try {
      const parsedUser = JSON.parse(storedUser);
      currentUserEmail = parsedUser?.email ?? '';
    } catch {
      this.passwordError = 'Unable to read logged user data.';
      return;
    }

    if (!currentUserEmail) {
      this.passwordError = 'User email not found for password update.';
      return;
    }

    this.isSubmitting = true;

    this.userService.updatePasswordByEmail(currentUserEmail, this.newPassword).subscribe({
      next: () => {
        this.currentPassword = '';
        this.newPassword = '';
        this.confirmPassword = '';
        this.passwordUpdated = true;
        this.isSubmitting = false;

        setTimeout(() => {
          this.passwordUpdated = false;
        }, 2400);
      },
      error: (error) => {
        this.isSubmitting = false;
        this.passwordError = error?.error?.message || 'Unable to update password. Please try again.';
      }
    });
  }
}
