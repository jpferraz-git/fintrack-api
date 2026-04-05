import { Component } from '@angular/core';
import { Sidebar } from '../../components/sidebar/sidebar.component';
import { DashboardMarketCards } from '../../components/dashboard/dashboard-market-cards/dashboard-market-cards.component';
import { DashboardChart } from '../../components/dashboard/dashboard-chart/dashboard-chart.component';
import { DashboardPriceAlerts } from '../../components/dashboard/dashboard-price-alerts/dashboard-price-alerts.component';
import { DashboardGainers } from '../../components/dashboard/dashboard-gainers/dashboard-gainers.component';
import { DashboardMarketTable } from '../../components/dashboard/dashboard-market-table/dashboard-market-table.component';
import { DashboardStatusbar } from '../../components/dashboard/dashboard-statusbar/dashboard-statusbar.component';
import { DashboardTopbar } from '../../components/dashboard/dashboard-topbar/dashboard-topbar.component';

@Component({
  selector: 'app-dashboard-page',
  imports: [
    Sidebar,
    DashboardTopbar,
    DashboardMarketCards,
    DashboardChart,
    DashboardPriceAlerts,
    DashboardGainers,
    DashboardMarketTable,
    DashboardStatusbar
  ],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardPage {}