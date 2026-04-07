import { Component, OnDestroy, OnInit } from '@angular/core';
import { Sidebar } from '../../components/sidebar/sidebar.component';
import { PortfolioTopbar } from '../../components/portfolio/portfolio-topbar/portfolio-topbar.component';
import { PortfolioValueCard } from '../../components/portfolio/portfolio-value-card/portfolio-value-card.component';
import { PortfolioActionCard } from '../../components/portfolio/portfolio-action-card/portfolio-action-card.component';
import { PortfolioRiskCard } from '../../components/portfolio/portfolio-risk-card/portfolio-risk-card.component';
import { PortfolioHoldingsTable } from '../../components/portfolio/portfolio-holdings-table/portfolio-holdings-table.component';
import { PortfolioTransactionsTable } from '../../components/portfolio/portfolio-transactions-table/portfolio-transactions-table.component';
import { PortfolioStatusbar } from '../../components/portfolio/portfolio-statusbar/portfolio-statusbar.component';
import { AddAssetModal } from '../../components/modals/add-asset-modal/add-asset-modal.component';

@Component({
  selector: 'app-portfolio-page',
  imports: [
    Sidebar,
    PortfolioTopbar,
    PortfolioValueCard,
    PortfolioActionCard,
    PortfolioTransactionsTable,
    PortfolioRiskCard,
    PortfolioHoldingsTable,
    PortfolioStatusbar,
    AddAssetModal
  ],
  templateUrl: './portfolio.component.html',
  styleUrl: './portfolio.component.css'
})
export class PortfolioPage implements OnInit, OnDestroy {
  isAddAssetModalOpen = false;
  refreshTrigger = 0;

  private refreshInterval: ReturnType<typeof setInterval> | null = null;

  ngOnInit(): void {
    this.startAutoRefresh();
  }

  ngOnDestroy(): void {
    if (this.refreshInterval !== null) {
      clearInterval(this.refreshInterval);
      this.refreshInterval = null;
    }
  }

  openAddAssetModal(): void {
    this.isAddAssetModalOpen = true;
  }

  closeAddAssetModal(): void {
    this.isAddAssetModalOpen = false;
  }

  handleAssetCreated(): void {
    this.refreshTrigger += 1;
  }

  private startAutoRefresh(): void {
    this.refreshInterval = setInterval(() => {
      this.refreshTrigger += 1;
    }, 5000);
  }
}
