import { Component } from '@angular/core';
import { MarketCardComponent } from '../market-data-kpi/market-card.component';

@Component({
  selector: 'app-dashboard-market-cards',
  imports: [MarketCardComponent],
  standalone: true,
  templateUrl: './dashboard-market-cards.component.html', 
  styleUrl: './dashboard-market-cards.component.css'
})
export class DashboardMarketCards {}