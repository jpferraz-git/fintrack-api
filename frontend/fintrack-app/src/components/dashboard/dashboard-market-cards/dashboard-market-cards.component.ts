import { ChangeDetectorRef, Component, OnDestroy, OnInit } from '@angular/core';
import { Subject, catchError, exhaustMap, of, takeUntil, timer } from 'rxjs';
import { MarketDataService } from '../../../app/services/market-data.service';
import { formatUsd } from '../../../app/shared/utils/sanitizer';

@Component({
  selector: 'app-dashboard-market-cards',
  imports: [],
  standalone: true,
  templateUrl: './dashboard-market-cards.component.html', 
  styleUrl: './dashboard-market-cards.component.css'
})
export class DashboardMarketCards implements OnInit, OnDestroy {

  private readonly destroy$ = new Subject<void>();
  btcPrice = '--';
  btcVolume = 'Vol: 0 USDT';
  btcDelta = 'LIVE';
  btcTrendUp = true;
  loadingBtc = false;

constructor(
  private marketDataService: MarketDataService,
  private cd: ChangeDetectorRef
) {}

  ngOnInit(): void {
    this.startBtcPolling();
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  private startBtcPolling(): void {
    this.loadingBtc = true;

    timer(0, 2000)
      .pipe(
        exhaustMap(() => this.marketDataService.getPrice('BTCUSDT')
          .pipe(catchError(() => of(null)))),
        takeUntil(this.destroy$)
      )
      .subscribe({
        next: (ticker) => {
          this.loadingBtc = false;

          if (!ticker) {
            this.btcDelta = 'OFFLINE';
            this.btcPrice = '--';
            this.btcVolume = 'Vol: --';
            this.btcTrendUp = true;
            return;
          }

          this.btcDelta = 'LIVE';
          this.btcPrice = formatUsd(ticker.price);
          this.cd.markForCheck();
        }
      });
  }
}