import { CommonModule } from '@angular/common';
import { Component, Input, OnChanges, OnDestroy, OnInit, SimpleChanges } from '@angular/core';
import { Subject, catchError, forkJoin, map, merge, of, switchMap, takeUntil, timer } from 'rxjs';
import { MarketDataService } from '../../../app/services/market-data.service';
import { PortfolioAssetResponse, PortfolioService } from '../../../app/services/portfolio.service';
import { UtilsService } from '../../../app/services/utils.service';

interface PortfolioHoldingRow {
  assetId: string
  symbol: string
  quantity: number
  positionValue: number
  currentPrice: number
  profitPercentage: number
}

@Component({
  selector: 'app-portfolio-holdings-table',
  imports: [CommonModule],
  templateUrl: './portfolio-holdings-table.component.html',
  styleUrl: './portfolio-holdings-table.component.css'
})
export class PortfolioHoldingsTable implements OnInit, OnChanges, OnDestroy {
  @Input() refreshTrigger = 0

  private readonly assetNames: Record<string, string> = {
    AVAX: 'Avalanche',
    BNB: 'BNB',
    BTC: 'Bitcoin',
    ADA: 'Cardano',
    LINK: 'Chainlink',
    DOGE: 'Dogecoin',
    ETH: 'Ethereum',
    LTC: 'Litecoin',
    DOT: 'Polkadot',
    SOL: 'Solana',
    TRX: 'TRON',
    XRP: 'XRP'
  }

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

  private readonly destroy$ = new Subject<void>()
  private readonly manualRefresh$ = new Subject<void>()

  constructor(
    private portfolioService: PortfolioService,
    private marketDataService: MarketDataService,
    private utilsService: UtilsService
  ) {}

  ngOnInit(): void {
    this.startPolling()
  }

  ngOnChanges(changes: SimpleChanges): void {
    const refreshTriggerChange = changes['refreshTrigger']
    if (refreshTriggerChange && !refreshTriggerChange.firstChange) {
      this.manualRefresh$.next()
    }
  }

  ngOnDestroy(): void {
    this.destroy$.next()
    this.destroy$.complete()
    this.manualRefresh$.complete()
  }

  trackByAssetId(index: number, row: PortfolioHoldingRow): string {
    return row.assetId
  }

  formatQuantity(quantity: number): string {
    return this.utilsService.formatQuantity(quantity, {
      minimumFractionDigits: 2
    })
  }

  formatPrice(price: number): string {
    return this.utilsService.formatUsd(price)
  }

  formatProfitPercentage(percentage: number): string {
    return this.utilsService.formatPercentage(percentage, { decimals: 2, includeSign: true })
  }

  getAssetTicker(symbol: string): string {
    const ticker = symbol.endsWith('USDT') ? symbol.slice(0, -4) : symbol
    return ticker.toUpperCase()
  }

  getAssetName(symbol: string): string {
    const ticker = this.getAssetTicker(symbol)
    return this.assetNames[ticker] ?? ticker
  }

  getAssetIcon(symbol: string): string {
    const ticker = this.getAssetTicker(symbol)
    return this.coinIconByCode[ticker] ?? ticker.toLowerCase()
  }

  private startPolling(): void {
    merge(timer(0, 2000), this.manualRefresh$)
      .pipe(
        switchMap(() => this.fetchRows()),
        takeUntil(this.destroy$)
      )
      .subscribe((rows) => {
        this.isLoading = false

        if (!rows) {
          this.rows = []
          this.hasError = true
          return
        }

        this.rows = rows.sort((a, b) => a.symbol.localeCompare(b.symbol))
        this.hasError = false
      })
  }

  private fetchRows() {
    return this.portfolioService.getAssets().pipe(
      switchMap((assets: PortfolioAssetResponse[]) => {
        if (assets.length === 0) {
          return of([] as PortfolioHoldingRow[])
        }

        const rowRequests = assets.map((asset) =>
          this.marketDataService.getPrice(asset.symbol).pipe(
            map((marketPrice) => this.toRow(asset, this.utilsService.parseNumeric(marketPrice.price))),
            catchError(() => of(this.toRow(asset, 0)))
          )
        )

        return forkJoin(rowRequests)
      }),
      catchError(() => of(null))
    )
  }

  private toRow(asset: PortfolioAssetResponse, currentPrice: number): PortfolioHoldingRow {
    const quantity = this.utilsService.parseNumeric(asset.quantity)
    const avgPrice = this.utilsService.parseNumeric(asset.avgPrice)
    const profitPercentage = avgPrice <= 0
      ? 0
      : ((currentPrice - avgPrice) / avgPrice) * 100
    const positionValue = quantity * currentPrice

    return {
      assetId: asset.assetId,
      symbol: asset.symbol,
      quantity,
      positionValue,
      currentPrice,
      profitPercentage
    }
  }
}
