import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MarketTableRowComponent } from './market-table-row.component';

interface MarketRow {
  symbol: string
  coinName: string
  coinIcon: string
  marketCap: string
}

@Component({
  selector: 'app-dashboard-market-table',
  imports: [CommonModule, FormsModule, MarketTableRowComponent],
  templateUrl: './dashboard-market-table.component.html',
  styleUrl: './dashboard-market-table.component.css'
})
export class DashboardMarketTable {
  searchTerm = ''

  readonly rows: MarketRow[] = [
    { symbol: 'AVAXUSDT', coinName: 'Avalanche', coinIcon: 'avax', marketCap: '$12.8B' },
    { symbol: 'BNBUSDT', coinName: 'BNB', coinIcon: 'bnb', marketCap: '$84.2B' },
    { symbol: 'BTCUSDT', coinName: 'Bitcoin', coinIcon: 'btc', marketCap: '$1.34T' },
    { symbol: 'ADAUSDT', coinName: 'Cardano', coinIcon: 'ada', marketCap: '$16.1B' },
    { symbol: 'LINKUSDT', coinName: 'Chainlink', coinIcon: 'link', marketCap: '$11.4B' },
    { symbol: 'DOGEUSDT', coinName: 'Dogecoin', coinIcon: 'doge', marketCap: '$25.4B' },
    { symbol: 'ETHUSDT', coinName: 'Ethereum', coinIcon: 'eth', marketCap: '$421.9B' },
    { symbol: 'LTCUSDT', coinName: 'Litecoin', coinIcon: 'ltc', marketCap: '$7.8B' },
    { symbol: 'DOTUSDT', coinName: 'Polkadot', coinIcon: 'dot', marketCap: '$10.2B' },
    { symbol: 'XRPUSDT', coinName: 'Ripple', coinIcon: 'xrp', marketCap: '$33.8B' },
    { symbol: 'SOLUSDT', coinName: 'Solana', coinIcon: 'sol', marketCap: '$64.5B' },
    { symbol: 'TRXUSDT', coinName: 'TRON', coinIcon: 'trx', marketCap: '$10.7B' }
  ]

  constructor() {}

  get filteredRows(): MarketRow[] {
    const normalizedSearch = this.searchTerm.trim().toLowerCase()
    if (!normalizedSearch) {
      return this.rows
    }

    return this.rows.filter((row) =>
      row.coinName.toLowerCase().includes(normalizedSearch)
      || row.symbol.toLowerCase().includes(normalizedSearch)
    )
  }

  clearSearch(): void {
    this.searchTerm = ''
  }

  trackBySymbol(index: number, row: MarketRow): string {
    return row.symbol
  }

}