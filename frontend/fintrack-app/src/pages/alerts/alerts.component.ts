import { Component } from '@angular/core';
import { Sidebar } from '../../components/sidebar/sidebar.component';
import { AlertsTopbar } from '../../components/alerts/alerts-topbar/alerts-topbar.component';
import { AlertsTable } from '../../components/alerts/alerts-table/alerts-table.component';
import { AlertsCreatePanel } from '../../components/alerts/alerts-create-panel/alerts-create-panel.component';
import { AlertsVolatility } from '../../components/alerts/alerts-volatility/alerts-volatility.component';
import { AlertsSystemMetrics } from '../../components/alerts/alerts-system-metrics/alerts-system-metrics.component';
import { AlertsStatusbar } from '../../components/alerts/alerts-statusbar/alerts-statusbar.component';
import { CreateAlertModal } from '../../components/modals/create-alert-modal/create-alert-modal.component';

@Component({
  selector: 'app-alerts-page',
  imports: [
    Sidebar,
    AlertsTopbar,
    AlertsTable,
    AlertsCreatePanel,
    AlertsVolatility,
    AlertsSystemMetrics,
    AlertsStatusbar,
    CreateAlertModal
  ],
  templateUrl: './alerts.component.html',
  styleUrl: './alerts.component.css'
})
export class AlertsPage {
  isCreateAlertModalOpen = false;

  openCreateAlertModal(): void {
    this.isCreateAlertModalOpen = true;
  }

  closeCreateAlertModal(): void {
    this.isCreateAlertModalOpen = false;
  }
}