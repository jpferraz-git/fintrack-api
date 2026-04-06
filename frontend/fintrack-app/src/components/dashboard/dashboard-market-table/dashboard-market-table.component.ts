import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { MarketTableRowComponent } from './market-table-row.component';

interface MarketRow {
  symbol: string
  coinName: string
  marketCap: string
}

@Component({
  selector: 'app-dashboard-market-table',
  imports: [CommonModule, MarketTableRowComponent],
  templateUrl: './dashboard-market-table.component.html',
  styleUrl: './dashboard-market-table.component.css'
})
export class DashboardMarketTable {
  readonly rows: MarketRow[] = [
    { symbol: 'SOLUSDT', coinName: 'Solana', marketCap: '--' },
    { symbol: 'ADAUSDT', coinName: 'Cardano', marketCap: '--' },
    { symbol: 'DOTUSDT', coinName: 'Polkadot', marketCap: '--' },
    { symbol: 'XRPUSDT', coinName: 'Ripple', marketCap: '--' }
  ]

  trackBySymbol(index: number, row: MarketRow): string {
    return row.symbol
  }
}