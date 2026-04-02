import { Component } from '@angular/core';
import { Sidebar } from '../../components/sidebar/sidebar';
import { DashboardTopbar } from '../../components/dashboard/dashboard-topbar/dashboard-topbar';
import { DashboardMarketCards } from '../../components/dashboard/dashboard-market-cards/dashboard-market-cards';
import { DashboardChart } from '../../components/dashboard/dashboard-chart/dashboard-chart';
import { DashboardPriceAlerts } from '../../components/dashboard/dashboard-price-alerts/dashboard-price-alerts';
import { DashboardGainers } from '../../components/dashboard/dashboard-gainers/dashboard-gainers';
import { DashboardMarketTable } from '../../components/dashboard/dashboard-market-table/dashboard-market-table';
import { DashboardStatusbar } from '../../components/dashboard/dashboard-statusbar/dashboard-statusbar';

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
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css'
})
export class DashboardPage {}