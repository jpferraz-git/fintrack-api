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
  newPassword = '';
  confirmPassword = '';
  showNewPassword = false;
  showConfirmPassword = false;

  passwordUpdated = false;
  passwordError = '';
  isSubmitting = false;

  constructor(private userService: UserService) {}

  updatePassword(): void {
    this.passwordError = '';
    this.passwordUpdated = false;

    if (!this.newPassword.trim() || !this.confirmPassword.trim()) {
      this.passwordError = 'New password and confirmation are required.';
      return;
    }

    const passwordValidationError = this.getPasswordValidationError(this.newPassword);
    if (passwordValidationError) {
      this.passwordError = passwordValidationError;
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
        this.newPassword = '';
        this.confirmPassword = '';
        this.passwordUpdated = true;
        this.isSubmitting = false;

        this.syncSessionUser(currentUserEmail);

        setTimeout(() => {
          if (typeof window !== 'undefined') {
            window.location.reload();
          }
        }, 700);

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

  toggleNewPasswordVisibility(): void {
    this.showNewPassword = !this.showNewPassword;
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

  private syncSessionUser(email: string): void {
    if (typeof window === 'undefined' || typeof sessionStorage === 'undefined') {
      return;
    }

    const localUserRaw = localStorage.getItem('user');
    let user = {
      email
    };

    if (localUserRaw) {
      try {
        user = {
          ...JSON.parse(localUserRaw),
          email
        };
      } catch {
        user = { email };
      }
    }

    sessionStorage.setItem('user', JSON.stringify(user));
    sessionStorage.setItem('security:lastPasswordUpdate', new Date().toISOString());
  }
}
