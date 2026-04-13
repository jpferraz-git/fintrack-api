import { Component, Input, OnDestroy, OnInit } from '@angular/core'
import { Subject, catchError, exhaustMap, forkJoin, of, takeUntil, timer } from 'rxjs'
import { MarketDataService } from '../../../app/services/market-data.service'
import { UtilsService } from '../../../app/services/utils.service'

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

  constructor(
    private marketDataService: MarketDataService,
    private utilsService: UtilsService
  ) {}

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
        
        this.price = this.utilsService.formatUsd(marketData.price.price)
        this.volume24h = this.utilsService.formatUsd(marketData.ticker24h.quoteVolume, {
          compact: true,
          minimumFractionDigits: 0
        })
        this.isPositive = !hasChange || changeValue >= 0
        this.changePercent = hasChange
          ? this.utilsService.formatPercentage(changeValue, { decimals: 2, includeSign: true })
          : '--'
        this.hasChangeData = hasChange  
      })
  }
}
