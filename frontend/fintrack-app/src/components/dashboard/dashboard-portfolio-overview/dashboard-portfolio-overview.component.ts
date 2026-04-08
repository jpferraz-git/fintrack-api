import { CommonModule } from '@angular/common';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { catchError, forkJoin, map, of, switchMap, takeUntil, timer, Subject } from 'rxjs';
import { MarketDataService } from '../../../app/services/market-data.service';
import { PortfolioAssetResponse, PortfolioService } from '../../../app/services/portfolio.service';

interface PortfolioOverviewRow {
  symbol: string
  code: string
  icon: string
  quantity: number
  value: number
  allocation: number
}

@Component({
  selector: 'app-dashboard-portfolio-overview',
  imports: [CommonModule, RouterLink],
  templateUrl: './dashboard-portfolio-overview.component.html',
  styleUrl: './dashboard-portfolio-overview.component.css'
})
export class DashboardPortfolioOverview implements OnInit, OnDestroy {
  private readonly destroy$ = new Subject<void>();

  readonly segmentColors = ['#4edea3', '#627eea', '#f7931a', '#f3ba2f', '#e74296', '#e84161'];
  readonly iconByCode: Record<string, string> = {
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
  };

  rows: PortfolioOverviewRow[] = [];
  isLoading = true;
  hasError = false;
  totalValue = 0;

  constructor(
    private portfolioService: PortfolioService,
    private marketDataService: MarketDataService
  ) {}

  ngOnInit(): void {
    this.startPolling();
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  get visibleRows(): PortfolioOverviewRow[] {
    return this.rows.slice(0, 5);
  }

  get chartBackground(): string {
    if (!this.rows.length) {
      return 'conic-gradient(rgba(115, 133, 161, 0.3) 0% 100%)';
    }

    let current = 0;
    const segments = this.visibleRows.map((row, index) => {
      const percentage = row.allocation;
      const start = current;
      current += percentage;
      const color = this.segmentColors[index % this.segmentColors.length];
      return `${color} ${start.toFixed(2)}% ${current.toFixed(2)}%`;
    });

    if (current < 100) {
      segments.push(`rgba(115, 133, 161, 0.25) ${current.toFixed(2)}% 100%`);
    }

    return `conic-gradient(${segments.join(', ')})`;
  }

  formatCurrency(value: number): string {
    return new Intl.NumberFormat('en-US', {
      style: 'currency',
      currency: 'USD',
      minimumFractionDigits: 2,
      maximumFractionDigits: 2
    }).format(value);
  }

  formatAllocation(value: number): string {
    return `${value.toFixed(1)}%`;
  }

  trackBySymbol(index: number, row: PortfolioOverviewRow): string {
    return row.symbol;
  }

  private startPolling(): void {
    timer(0, 5000)
      .pipe(
        switchMap(() => this.loadRows()),
        takeUntil(this.destroy$)
      )
      .subscribe((result) => {
        this.isLoading = false;

        if (!result) {
          this.rows = [];
          this.totalValue = 0;
          this.hasError = true;
          return;
        }

        this.rows = result;
        this.totalValue = result.reduce((acc, row) => acc + row.value, 0);
        this.hasError = false;
      });
  }

  private loadRows() {
    return this.portfolioService.getAssets().pipe(
      switchMap((assets: PortfolioAssetResponse[]) => {
        if (!assets.length) {
          return of([] as PortfolioOverviewRow[]);
        }

        const requests = assets.map((asset) =>
          this.marketDataService.getPrice(asset.symbol).pipe(
            map((priceResponse) => this.toRawRow(asset, this.parseNumber(priceResponse.price))),
            catchError(() => of(this.toRawRow(asset, 0)))
          )
        );

        return forkJoin(requests).pipe(
          map((rows) => this.toRowsWithAllocation(rows))
        );
      }),
      catchError(() => of(null))
    );
  }

  private toRawRow(asset: PortfolioAssetResponse, currentPrice: number): PortfolioOverviewRow {
    const quantity = this.parseNumber(asset.quantity);
    const code = this.toCode(asset.symbol);
    const icon = this.iconByCode[code] ?? code.toLowerCase();

    return {
      symbol: asset.symbol,
      code,
      icon,
      quantity,
      value: quantity * currentPrice,
      allocation: 0
    };
  }

  private toRowsWithAllocation(rows: PortfolioOverviewRow[]): PortfolioOverviewRow[] {
    const sortedRows = [...rows]
      .filter((row) => row.quantity > 0)
      .sort((first, second) => second.value - first.value);

    const total = sortedRows.reduce((acc, row) => acc + row.value, 0);

    return sortedRows.map((row) => ({
      ...row,
      allocation: total > 0 ? (row.value / total) * 100 : 0
    }));
  }

  private toCode(symbol: string): string {
    return symbol.endsWith('USDT') ? symbol.slice(0, -4).toUpperCase() : symbol.toUpperCase();
  }

  private parseNumber(value: number | string): number {
    const parsed = typeof value === 'number' ? value : Number(value);
    return Number.isFinite(parsed) ? parsed : 0;
  }
}
