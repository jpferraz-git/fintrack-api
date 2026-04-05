import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-settings-identity-card',
  imports: [FormsModule],
  templateUrl: './settings-identity-card.component.html',
  styleUrl: './settings-identity-card.component.css'
})
export class SettingsIdentityCard {
  fullName = 'Alexander Sterling';
  email = 'a.sterling@obsidian-terminal.com';
  profileSaved = false;

  saveIdentity(): void {
    this.profileSaved = true;
    setTimeout(() => {
      this.profileSaved = false;
    }, 2200);
  }
}
