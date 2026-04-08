import { Component, Input, OnDestroy, OnInit } from '@angular/core'
import { Subject, catchError, exhaustMap, forkJoin, of, takeUntil, timer } from 'rxjs'
import { MarketDataService } from '../../../app/services/market-data.service'
import { formatUsd } from '../../../app/shared/utils/sanitizer'

@Component({
  selector: 'tr[app-market-table-row]',
  standalone: true,
  templateUrl: './market-table-row.component.html',
  styleUrl: './market-table-row.component.css'
})
export class MarketTableRowComponent implements OnInit, OnDestroy {
  private readonly destroy$ = new Subject<void>()

  @Input({ required: true }) symbol!: string
  @Input({ required: true }) coinName!: string
  @Input({ required: true }) coinIcon!: string
  @Input() marketCap = '--'

  price = '--'
  changePercent = '--'
  volume24h = '--'
  marketCapDisplay = '--'
  loading = true
  isPositive = true
  isFollowing = false
  hasChangeData = false

  get coinPairLabel(): string {
    return this.symbol.replace('USDT', '/USDT')
  }

  constructor(private marketDataService: MarketDataService) {}

  ngOnInit(): void {
    this.marketCapDisplay = this.marketCap || '--'
    this.startPolling()
  }

  ngOnDestroy(): void {
    this.destroy$.next()
    this.destroy$.complete()
  }

  toggleWatch(): void {
    this.isFollowing = !this.isFollowing
  }

  private startPolling(): void {
    timer(0, 1000)
      .pipe(
        exhaustMap(() =>
          forkJoin({
            price: this.marketDataService.getPrice(this.symbol),
            ticker24h: this.marketDataService.get24hTicker(this.symbol)
          }).pipe(catchError(() => of(null)))
        ),
        takeUntil(this.destroy$)
      )
      .subscribe((marketData) => {
        this.loading = false

        if (!marketData) {
          this.price = '--'
          this.changePercent = '--'
          this.volume24h = '--'
          this.isPositive = true
          return
        }

        const changeValue = Number(marketData.ticker24h.priceChangePercent)
        const hasChange = Number.isFinite(changeValue)
        
        this.price = formatUsd(marketData.price.price)
        this.volume24h = this.formatCompactUsd(marketData.ticker24h.quoteVolume)
        this.isPositive = !hasChange || changeValue >= 0
        this.changePercent = hasChange ? `${changeValue >= 0 ? '+' : ''}${changeValue.toFixed(2)}%` : '--'
        this.hasChangeData = hasChange  
      })
  }

  private formatCompactUsd(value: number | string): string {
    const parsedValue = Number(value)
    if (!Number.isFinite(parsedValue)) {
      return '--'
    }

    return new Intl.NumberFormat('en-US', {
      style: 'currency',
      currency: 'USD',
      notation: 'compact',
      maximumFractionDigits: 2
    }).format(parsedValue)
  }
}
