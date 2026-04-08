import { Component } from '@angular/core';
import { Sidebar } from '../../components/sidebar/sidebar.component';
import { PortfolioTopbar } from '../../components/portfolio/portfolio-topbar/portfolio-topbar.component';
import { PortfolioValueCard } from '../../components/portfolio/portfolio-value-card/portfolio-value-card.component';
import { PortfolioActionCard } from '../../components/portfolio/portfolio-action-card/portfolio-action-card.component';
import { PortfolioHoldingsTable } from '../../components/portfolio/portfolio-holdings-table/portfolio-holdings-table.component';
import { PortfolioTransactionsTable } from '../../components/portfolio/portfolio-transactions-table/portfolio-transactions-table.component';
import { PortfolioStatusbar } from '../../components/portfolio/portfolio-statusbar/portfolio-statusbar.component';
import { AddAssetModal } from '../../components/modals/add-asset-modal/add-asset-modal.component';
import { SellAssetModal } from '../../components/modals/sell-asset-modal/sell-asset-modal.component';

@Component({
  selector: 'app-portfolio-page',
  imports: [
    Sidebar,
    PortfolioTopbar,
    PortfolioValueCard,
    PortfolioActionCard,
    PortfolioTransactionsTable,
    PortfolioHoldingsTable,
    PortfolioStatusbar,
    AddAssetModal,
    SellAssetModal
  ],
  templateUrl: './portfolio.component.html',
  styleUrl: './portfolio.component.css'
})
export class PortfolioPage {
  isAddAssetModalOpen = false;
  isSellAssetModalOpen = false;
  refreshTrigger = 0;

  openAddAssetModal(): void {
    this.isAddAssetModalOpen = true;
  }

  closeAddAssetModal(): void {
    this.isAddAssetModalOpen = false;
  }

  openSellAssetModal(): void {
    this.isSellAssetModalOpen = true;
  }

  closeSellAssetModal(): void {
    this.isSellAssetModalOpen = false;
  }

  handleAssetCreated(): void {
    this.refreshTrigger += 1;
  }

  handleAssetSold(): void {
    this.refreshTrigger += 1;
  }
}
