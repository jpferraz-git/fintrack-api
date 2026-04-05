import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Sidebar } from '../../components/sidebar/sidebar.component';

@Component({
  selector: 'app-settings-page',
  imports: [FormsModule, Sidebar],
  templateUrl: './settings.component.html',
  styleUrl: './settings.component.css'
})
export class SettingsPage {
  fullName = 'Alexander Sterling';
  email = 'a.sterling@obsidian-terminal.com';

  currentPassword = '';
  newPassword = '';
  confirmPassword = '';

  priceVolatilityAlerts = true;
  orderExecutionLogs = true;
  securityBreaches = true;
  systemMaintenance = false;

  profileSaved = false;
  passwordUpdated = false;
  passwordError = '';

  saveIdentity(): void {
    this.profileSaved = true;
    setTimeout(() => {
      this.profileSaved = false;
    }, 2200);
  }

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
