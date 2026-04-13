import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../../app/services/auth.service';
import { UserService } from '../../../app/services/user.service';
import { UtilsService } from '../../../app/services/utils.service';

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

  isDeleteModalOpen = false;
  isDeletingAccount = false;
  deleteAccountError = '';

  passwordUpdated = false;
  passwordError = '';
  isSubmitting = false;

  constructor(
    private userService: UserService,
    private authService: AuthService,
    private router: Router,
    private utilsService: UtilsService
  ) {}

  updatePassword(): void {
    this.passwordError = '';
    this.passwordUpdated = false;

    if (!this.newPassword.trim() || !this.confirmPassword.trim()) {
      this.passwordError = 'New password and confirmation are required.';
      return;
    }

    const passwordValidationError = this.utilsService.validatePassword(this.newPassword);
    if (passwordValidationError) {
      this.passwordError = passwordValidationError;
      return;
    }

    if (this.newPassword !== this.confirmPassword) {
      this.passwordError = 'New password and confirmation do not match.';
      return;
    }

    const currentUserEmail = this.utilsService.getStoredUserEmail();
    if (!currentUserEmail) {
      this.passwordError = 'Unable to identify logged user.';
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
    this.showNewPassword = this.utilsService.toggleBoolean(this.showNewPassword);
  }

  toggleConfirmPasswordVisibility(): void {
    this.showConfirmPassword = this.utilsService.toggleBoolean(this.showConfirmPassword);
  }

  openDeleteAccountModal(): void {
    this.deleteAccountError = '';
    this.isDeleteModalOpen = true;
  }

  closeDeleteAccountModal(): void {
    if (this.isDeletingAccount) {
      return;
    }

    this.deleteAccountError = '';
    this.isDeleteModalOpen = false;
  }

  onDeleteModalBackdropClick(event: MouseEvent): void {
    if (event.target === event.currentTarget) {
      this.closeDeleteAccountModal();
    }
  }

  confirmDeleteAccount(): void {
    if (this.isDeletingAccount) {
      return;
    }

    this.deleteAccountError = '';

    const currentUserEmail = this.utilsService.getStoredUserEmail();
    if (!currentUserEmail) {
      this.deleteAccountError = 'Unable to identify logged user.';
      return;
    }

    this.isDeletingAccount = true;

    this.userService.deleteUserByEmail(currentUserEmail).subscribe({
      next: () => {
        this.isDeletingAccount = false;
        this.authService.logout();
        this.isDeleteModalOpen = false;
        this.router.navigate(['/login']);
      },
      error: (error) => {
        this.isDeletingAccount = false;
        this.deleteAccountError = error?.error?.message || 'Unable to delete account. Please try again.';
      }
    });
  }

  private syncSessionUser(email: string): void {
    if (typeof window === 'undefined' || typeof sessionStorage === 'undefined') {
      return;
    }

    let user = {
      email
    };

    const storedUser = this.utilsService.getStoredUser<Record<string, unknown>>();
    if (storedUser) {
      user = {
        ...storedUser,
        email
      };
    }

    sessionStorage.setItem('user', JSON.stringify(user));
    sessionStorage.setItem('security:lastPasswordUpdate', new Date().toISOString());
  }
}
