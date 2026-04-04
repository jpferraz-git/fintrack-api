import { Component, Input, OnDestroy, OnInit, ChangeDetectorRef } from '@angular/core';
import { UpperCasePipe } from '@angular/common';
import { Subject, catchError, exhaustMap, forkJoin, of, takeUntil, timer } from 'rxjs';
import { MarketDataService } from '../../../app/services/market-data.service';
import { formatUsd } from '../../../app/shared/utils/sanitizer';

@Component({
  selector: 'app-market-card',
  standalone: true,
  imports: [UpperCasePipe],
  templateUrl: './market-card.component.html',
  styleUrl: './market-card.component.css'
})
export class MarketCardComponent implements OnInit, OnDestroy {

  private readonly destroy$ = new Subject<void>();
  private readonly sparklineHistory: number[] = [];
  private readonly maxHistoryPoints = 16;

  @Input() symbol!: string;
  @Input() label!: string;
  @Input() coin!: string;

  price = '--';
  volume = 'Vol: --';
  delta = 'LIVE';
  trendUp = true;
  sparklinePoints = '2,18 14,16 24,17 34,13 44,11 56,12 66,8 78,4';
  loading = true;

  constructor(
    private marketDataService: MarketDataService,
    private cd: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.startPolling();
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  private startPolling(): void {
    this.loading = true;

    timer(0, 2000)
      .pipe(
        exhaustMap(() => forkJoin({
          price: this.marketDataService.getPrice(this.symbol),
          klines: this.marketDataService.getKlines(this.symbol, '1m')
        }).pipe(catchError(() => of(null)))),
        takeUntil(this.destroy$)
      )
      .subscribe((ticker) => {
        this.loading = false;

        if (!ticker) {
          this.delta = 'OFFLINE';
          this.price = '--';
          this.volume = 'Vol: --';
          this.trendUp = true;
          this.sparklinePoints = '2,18 14,16 24,17 34,13 44,11 56,12 66,8 78,4';
          this.sparklineHistory.length = 0;
          return;
        }

        const high = Number(ticker.klines.high);
        const low = Number(ticker.klines.low);

        this.trendUp = Number.isFinite(high) && Number.isFinite(low)
          ? high >= low
          : true;

        if (Number.isFinite(low) && Number.isFinite(high)) {
          // Preserve real values (low/high) over time so the sparkline reflects real market movement.
          if (this.trendUp) {
            this.sparklineHistory.push(low, high);
          } else {
            this.sparklineHistory.push(high, low);
          }

          if (this.sparklineHistory.length > this.maxHistoryPoints) {
            this.sparklineHistory.splice(0, this.sparklineHistory.length - this.maxHistoryPoints);
          }

          this.sparklinePoints = this.toSparklinePoints(this.sparklineHistory);
        }

        this.delta = 'LIVE';
        this.price = formatUsd(ticker.price.price);
        this.volume = `Pair: ${ticker.price.symbol}`;
        this.cd.markForCheck();
      });
  }

  private toSparklinePoints(values: number[]): string {
    if (values.length === 0) {
      return '2,18 14,16 24,17 34,13 44,11 56,12 66,8 78,4';
    }

    if (values.length === 1) {
      return '2,12 78,12';
    }

    const min = Math.min(...values);
    const max = Math.max(...values);
    const span = max - min;
    const left = 2;
    const right = 78;
    const top = 4;
    const bottom = 20;

    return values
      .map((value, index) => {
        const x = left + ((right - left) * index) / (values.length - 1);
        const y = span === 0
          ? (top + bottom) / 2
          : top + ((max - value) * (bottom - top)) / span;

        return `${x.toFixed(1)},${y.toFixed(1)}`;
      })
      .join(' ');
  }

}