import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-settings-alert-logic',
  imports: [FormsModule],
  templateUrl: './settings-alert-logic.component.html',
  styleUrl: './settings-alert-logic.component.css'
})
export class SettingsAlertLogic {
  priceVolatilityAlerts = true;
  orderExecutionLogs = true;
  securityBreaches = true;
  systemMaintenance = false;
}
