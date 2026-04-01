import { Component } from '@angular/core';
import { AlertsSidebar } from '../../components/alerts/alerts-sidebar/alerts-sidebar';
import { AlertsTopbar } from '../../components/alerts/alerts-topbar/alerts-topbar';
import { AlertsTable } from '../../components/alerts/alerts-table/alerts-table';
import { AlertsCreatePanel } from '../../components/alerts/alerts-create-panel/alerts-create-panel';
import { AlertsVolatility } from '../../components/alerts/alerts-volatility/alerts-volatility';
import { AlertsSystemMetrics } from '../../components/alerts/alerts-system-metrics/alerts-system-metrics';
import { AlertsStatusbar } from '../../components/alerts/alerts-statusbar/alerts-statusbar';
import { CreateAlertModal } from '../../components/models/create-alert-modal/create-alert-modal';

@Component({
  selector: 'app-alerts-page',
  imports: [
    AlertsSidebar,
    AlertsTopbar,
    AlertsTable,
    AlertsCreatePanel,
    AlertsVolatility,
    AlertsSystemMetrics,
    AlertsStatusbar,
    CreateAlertModal
  ],
  templateUrl: './alerts.html'
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