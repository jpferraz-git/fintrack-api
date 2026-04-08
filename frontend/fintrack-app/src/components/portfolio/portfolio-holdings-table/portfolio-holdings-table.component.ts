import { CommonModule } from '@angular/common';
import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { catchError, forkJoin, map, of, switchMap } from 'rxjs';
import { MarketDataService } from '../../../app/services/market-data.service';
import { PortfolioAssetResponse, PortfolioService } from '../../../app/services/portfolio.service';

interface PortfolioHoldingRow {
  assetId: string
  symbol: string
  quantity: number
  avgPrice: number
  currentPrice: number
  profitPercentage: number
}

@Component({
  selector: 'app-portfolio-holdings-table',
  imports: [CommonModule],
  templateUrl: './portfolio-holdings-table.component.html',
  styleUrl: './portfolio-holdings-table.component.css'
})
export class PortfolioHoldingsTable implements OnInit, OnChanges {
  @Input() refreshTrigger = 0

  private readonly coinIconByCode: Record<string, string> = {
    BTC: 'btc',
    ETH: 'eth',
    BNB: 'bnb',
    SOL: 'sol',
    XRP: 'xrp',
    ADA: 'ada',
    DOGE: 'doge',
    AVAX: 'avax',
    DOT: 'dot',
    LINK: 'link',
    LTC: 'ltc',
    TRX: 'trx'
  }

  rows: PortfolioHoldingRow[] = []
  isLoading = true
  hasError = false

  constructor(
    private portfolioService: PortfolioService,
    private marketDataService: MarketDataService
  ) {}

  ngOnInit(): void {
    this.loadRows()
  }

  ngOnChanges(changes: SimpleChanges): void {
    const refreshTriggerChange = changes['refreshTrigger']
    if (refreshTriggerChange && !refreshTriggerChange.firstChange) {
      this.loadRows()
    }
  }

  trackByAssetId(index: number, row: PortfolioHoldingRow): string {
    return row.assetId
  }

  formatQuantity(quantity: number): string {
    return quantity.toLocaleString('en-US', {
      minimumFractionDigits: 2,
      maximumFractionDigits: 8
    })
  }

  formatPrice(price: number): string {
    return new Intl.NumberFormat('en-US', {
      style: 'currency',
      currency: 'USD',
      minimumFractionDigits: 2,
      maximumFractionDigits: 2
    }).format(price)
  }

  formatProfitPercentage(percentage: number): string {
    const sign = percentage > 0 ? '+' : ''
    return `${sign}${percentage.toFixed(2)}%`
  }

  symbolLabel(symbol: string): string {
    return symbol.endsWith('USDT') ? symbol.slice(0, -4) : symbol
  }

  symbolIcon(symbol: string): string {
    const code = this.symbolLabel(symbol).toUpperCase()
    return this.coinIconByCode[code] ?? code.toLowerCase()
  }

  private loadRows(): void {
    this.isLoading = true
    this.hasError = false

    this.portfolioService
      .getAssets()
      .pipe(
        switchMap((assets: PortfolioAssetResponse[]) => {
          if (assets.length === 0) {
            return of([] as PortfolioHoldingRow[])
          }

          const rowRequests = assets.map((asset) =>
            this.marketDataService.getPrice(asset.symbol).pipe(
              map((marketPrice) => this.toRow(asset, this.parseNumeric(marketPrice.price))),
              catchError(() => of(this.toRow(asset, 0)))
            )
          )

          return forkJoin(rowRequests)
        })
      )
      .subscribe({
        next: (rows: PortfolioHoldingRow[]) => {
          this.rows = rows.sort((a, b) => a.symbol.localeCompare(b.symbol))
          this.isLoading = false
        },
        error: () => {
          this.rows = []
          this.hasError = true
          this.isLoading = false
        }
      })
  }

  private toRow(asset: PortfolioAssetResponse, currentPrice: number): PortfolioHoldingRow {
    const quantity = this.parseNumeric(asset.quantity)
    const avgPrice = this.parseNumeric(asset.avgPrice)
    const profitPercentage = avgPrice <= 0
      ? 0
      : ((currentPrice - avgPrice) / avgPrice) * 100

    return {
      assetId: asset.assetId,
      symbol: asset.symbol,
      quantity,
      avgPrice,
      currentPrice,
      profitPercentage
    }
  }

  private parseNumeric(value: number | string): number {
    const parsed = typeof value === 'number' ? value : Number(value)
    return Number.isFinite(parsed) ? parsed : 0
  }
}
