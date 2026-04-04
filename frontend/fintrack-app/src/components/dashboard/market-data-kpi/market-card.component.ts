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

        const latestKline = Array.isArray(ticker.klines)
          ? ticker.klines.at(-1)
          : ticker.klines;

        if (!latestKline) {
          this.isOffline = true;
          this.price = '--';
          this.volume = 'Vol: --';
          this.trendUp = true;
          return;
        }

        const high = Number(latestKline.high);
        const low = Number(latestKline.low);

        this.trendUp = Number.isFinite(high) && Number.isFinite(low)
          ? high >= low
          : true;

        this.isOffline = false;
        this.price = formatUsd(ticker.price.price);
        this.volume = `Pair: ${ticker.price.symbol}`;
        this.cd.markForCheck();
      });
  }

}