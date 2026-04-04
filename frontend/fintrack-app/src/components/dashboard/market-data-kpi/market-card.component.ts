import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { UpperCasePipe } from '@angular/common';
import { Subject, catchError, exhaustMap, of, takeUntil, timer } from 'rxjs';
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
  private previousPrice: number | null = null;

  @Input() symbol!: string;
  @Input() label!: string;
  @Input() coin!: string;

  price = '--';
  volume = 'Vol: --';
  delta = 'LIVE';
  trendUp = true;
  loading = true;

  constructor(private marketDataService: MarketDataService) {}

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
        exhaustMap(() => this.marketDataService.getPrice(this.symbol)
          .pipe(catchError(() => of(null)))),
        takeUntil(this.destroy$)
      )
      .subscribe((ticker) => {
        this.loading = false;

        if (!ticker) {
          this.delta = 'OFFLINE';
          this.price = '--';
          this.volume = 'Vol: --';
          this.trendUp = true;
          this.previousPrice = null;
          return;
        }

        const currentPrice = Number(ticker.price);
        if (!Number.isNaN(currentPrice) && this.previousPrice !== null) {
          this.trendUp = currentPrice >= this.previousPrice;
        }

        this.previousPrice = Number.isNaN(currentPrice) ? this.previousPrice : currentPrice;
        this.delta = 'LIVE';
        this.price = formatUsd(ticker.price);
        this.volume = `Pair: ${ticker.symbol}`;
      });
  }

}