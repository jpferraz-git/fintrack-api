import { Component } from '@angular/core';
import { Sidebar } from '../../components/sidebar/sidebar.component';
import { SettingsHeader } from '../../components/settings/settings-header/settings-header.component';
import { SettingsIdentityCard } from '../../components/settings/settings-identity-card/settings-identity-card.component';
import { SettingsSecurityCard } from '../../components/settings/settings-security-card/settings-security-card.component';
import { SettingsAlertLogic } from '../../components/settings/settings-alert-logic/settings-alert-logic.component';
import { SettingsSyncPanel } from '../../components/settings/settings-sync-panel/settings-sync-panel.component';

@Component({
  selector: 'app-settings-page',
  imports: [
    Sidebar,
    SettingsHeader,
    SettingsIdentityCard,
    SettingsSecurityCard,
    SettingsAlertLogic,
    SettingsSyncPanel
  ],
  templateUrl: './settings.component.html',
  styleUrl: './settings.component.css'
})
export class SettingsPage {}
