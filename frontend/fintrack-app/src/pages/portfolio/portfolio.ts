import { Component } from '@angular/core';
import { PortfolioSidebar } from '../../components/portfolio/portfolio-sidebar/portfolio-sidebar';
import { PortfolioTopbar } from '../../components/portfolio/portfolio-topbar/portfolio-topbar';
import { PortfolioValueCard } from '../../components/portfolio/portfolio-value-card/portfolio-value-card';
import { PortfolioActionCard } from '../../components/portfolio/portfolio-action-card/portfolio-action-card';
import { PortfolioRiskCard } from '../../components/portfolio/portfolio-risk-card/portfolio-risk-card';
import { PortfolioHoldingsTable } from '../../components/portfolio/portfolio-holdings-table/portfolio-holdings-table';
import { PortfolioStatusbar } from '../../components/portfolio/portfolio-statusbar/portfolio-statusbar';
import { AddAssetModal } from '../../components/models/add-asset-modal/add-asset-modal';

@Component({
  selector: 'app-portfolio-page',
  imports: [
    PortfolioSidebar,
    PortfolioTopbar,
    PortfolioValueCard,
    PortfolioActionCard,
    PortfolioRiskCard,
    PortfolioHoldingsTable,
    PortfolioStatusbar,
    AddAssetModal
  ],
  templateUrl: './portfolio.html'
})
export class PortfolioPage {
  isAddAssetModalOpen = false;

  openAddAssetModal(): void {
    this.isAddAssetModalOpen = true;
  }

  closeAddAssetModal(): void {
    this.isAddAssetModalOpen = false;
  }
}
