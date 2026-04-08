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

  @Input() symbol!: string;
  @Input() label!: string;
  @Input() coin!: string;

  price = '--';
  volume = 'Vol: --';
  isOffline = false;
  trendUp = true;
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
          this.isOffline = true;
          this.price = '--';
          this.volume = 'Vol: --';
          this.trendUp = true;
          return;
        }

        const klineRows = Array.isArray(ticker.klines)
          ? ticker.klines
          : [ticker.klines];

        const latestKline = klineRows.at(-1);
        const previousKline = klineRows.length > 1 ? klineRows.at(-2) : undefined;

        if (!latestKline) {
          this.isOffline = true;
          this.price = '--';
          this.volume = 'Vol: --';
          this.trendUp = true;
          return;
        }

        this.trendUp = this.resolveTrendDirection(
          latestKline,
          previousKline,
          ticker.price.price,
          this.trendUp
        );

        this.isOffline = false;
        this.price = formatUsd(ticker.price.price);
        this.volume = `Pair: ${ticker.price.symbol}`;
        this.cd.markForCheck();
      });
  }

  private resolveTrendDirection(
    latestKline: { open: number | string; close: number | string },
    previousKline: { close: number | string } | undefined,
    currentPrice: number | string,
    fallback: boolean
  ): boolean {
    const open = Number(latestKline.open);
    const close = Number(latestKline.close);

    if (Number.isFinite(open) && Number.isFinite(close) && close !== open) {
      return close > open;
    }

    const previousClose = Number(previousKline?.close);
    if (Number.isFinite(previousClose) && Number.isFinite(close) && close !== previousClose) {
      return close > previousClose;
    }

    const livePrice = Number(currentPrice);
    if (Number.isFinite(livePrice) && Number.isFinite(close) && livePrice !== close) {
      return livePrice > close;
    }

    return fallback;
  }

}