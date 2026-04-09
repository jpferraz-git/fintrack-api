import { NgFor } from '@angular/common';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subject, catchError, of, switchMap, takeUntil, timer } from 'rxjs';
import { MarketDataService, MarketIndividualKlineResponse, MarketKlinesResponse } from '../../../app/services/market-data.service';
import {
  ChartIntervalOption,
  DashboardChartIntervalSelectorComponent
} from './interval-selector/dashboard-chart-interval-selector.component';
import {
  ChartSymbolOption,
  DashboardChartSymbolSelectorComponent
} from './symbol-selector/dashboard-chart-symbol-selector.component';

type CandlePoint = {
  open: number;
  high: number;
  low: number;
  close: number;
};

type RenderCandle = {
  candle: CandlePoint;
  trend: 'up' | 'down';
  wickTop: string;
  wickBottom: string;
  bodyTop: string;
  bodyHeight: string;
};

type ChartInterval = '1m' | '15m' | '1h' | '1d' | '1w';

@Component({
  selector: 'app-dashboard-chart',
  imports: [NgFor, DashboardChartIntervalSelectorComponent, DashboardChartSymbolSelectorComponent],
  templateUrl: './dashboard-chart.component.html',
  styleUrl: './dashboard-chart.component.css'
})
export class DashboardChart implements OnInit, OnDestroy {
  private readonly destroy$ = new Subject<void>();
  private readonly refreshParams$ = new Subject<void>();

  readonly intervalOptions: ChartIntervalOption[] = [
    { value: '1m', label: '1m' },
    { value: '15m', label: '15m' },
    { value: '1h', label: '1h' },
    { value: '1d', label: '1d' },
    { value: '1w', label: '1w' }
  ];

  readonly symbolOptions: ChartSymbolOption[] = [
    { symbol: 'AVAXUSDT', label: 'Avalanche' },
    { symbol: 'BNBUSDT', label: 'BNB' },
    { symbol: 'BTCUSDT', label: 'Bitcoin' },
    { symbol: 'ADAUSDT', label: 'Cardano' },
    { symbol: 'LINKUSDT', label: 'Chainlink' },
    { symbol: 'DOGEUSDT', label: 'Dogecoin' },
    { symbol: 'ETHUSDT', label: 'Ethereum' },
    { symbol: 'LTCUSDT', label: 'Litecoin' },
    { symbol: 'DOTUSDT', label: 'Polkadot' },
    { symbol: 'XRPUSDT', label: 'Ripple' },
    { symbol: 'SOLUSDT', label: 'Solana' },
    { symbol: 'TRXUSDT', label: 'TRON' }
  ];

  selectedInterval: ChartInterval = '15m';
  selectedSymbol = 'BTCUSDT';

  candles: RenderCandle[] = [];
  isOffline = false;
  loading = true;
  tooltipVisible = false;
  tooltipText = '';
  tooltipLeft = 0;
  tooltipTop = 0;

  constructor(private marketDataService: MarketDataService) {}

  ngOnInit(): void {
    this.startPolling();
    this.refreshParams$.next();
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  get selectedSymbolLabel(): string {
    return this.symbolOptions.find((option) => option.symbol === this.selectedSymbol)?.label ?? this.selectedSymbol;
  }

  get liveStatusLabel(): string {
    if (this.loading) {
      return 'LOADING';
    }

    if (this.isOffline) {
      return 'OFFLINE';
    }

    return 'REAL-TIME';
  }

  trackByIndex(index: number): number {
    return index;
  }

  onIntervalSelected(interval: string): void {
    if (this.selectedInterval === interval) {
      return;
    }

    this.selectedInterval = interval as ChartInterval;
    this.refreshParams$.next();
  }

  onSymbolSelected(symbol: string): void {
    if (this.selectedSymbol === symbol) {
      return;
    }

    this.selectedSymbol = symbol;
    this.refreshParams$.next();
  }

  private buildCandles(series: CandlePoint[]): RenderCandle[] {
    const minLow = Math.min(...series.map((point) => point.low));
    const maxHigh = Math.max(...series.map((point) => point.high));
    const range = Math.max(maxHigh - minLow, 1);

    const toPercent = (price: number): number => ((price - minLow) / range) * 100;
    const fromTop = (price: number): string => `${100 - toPercent(price)}%`;

    return series.map((point) => {
      const upperBody = Math.max(point.open, point.close);
      const lowerBody = Math.min(point.open, point.close);
      const bodySize = Math.abs(toPercent(point.open) - toPercent(point.close));

      return {
        candle: point,
        trend: point.close >= point.open ? 'up' : 'down',
        wickTop: fromTop(point.high),
        wickBottom: `${toPercent(point.low)}%`,
        bodyTop: fromTop(upperBody),
        bodyHeight: `${Math.max(bodySize, 2)}%`
      };
    });
  }

 
  private startPolling(): void {
    this.refreshParams$
      .pipe(
        switchMap(() =>
          timer(0, 4000).pipe(
            switchMap(() => {
              this.loading = true;

              return this.marketDataService.getKlines(this.selectedSymbol, this.selectedInterval).pipe(
                catchError(() => of([] as MarketIndividualKlineResponse[]))
              );
            })
          )
        ),
        takeUntil(this.destroy$)
      )
      .subscribe((response) => {
        const series = this.toCandleSeries(response);
        this.loading = false;

        if (!series.length) {
          this.isOffline = true;
          return;
        }

        this.isOffline = false;
        this.candles = this.buildCandles(series);
      });
  }

  getTooltip(renderCandle: RenderCandle): string {
    const { open, high, low, close } = renderCandle.candle;
    return `Open: ${open} | High: ${high} | Low: ${low} | Close: ${close}`;
  }

  onCandleMouseEnter(event: MouseEvent, candle: RenderCandle, canvas: HTMLElement): void {
    this.tooltipText = this.getTooltip(candle);
    this.tooltipVisible = true;
    this.updateTooltipPosition(event, canvas);
  }

  onCandleMouseMove(event: MouseEvent, canvas: HTMLElement): void {
    if (!this.tooltipVisible) {
      return;
    }

    this.updateTooltipPosition(event, canvas);
  }

  onCandleMouseLeave(): void {
    this.tooltipVisible = false;
  }

  private updateTooltipPosition(event: MouseEvent, canvas: HTMLElement): void {
    const rect = canvas.getBoundingClientRect();
    const cursorX = event.clientX - rect.left;
    const cursorY = event.clientY - rect.top;
    const tooltipWidth = 190;

    this.tooltipLeft = Math.min(Math.max(cursorX, 12), Math.max(rect.width - tooltipWidth, 12));
    this.tooltipTop = Math.max(cursorY - 14, 14);
  }
  
  private toCandleSeries(response: MarketKlinesResponse): CandlePoint[] {
    const rows = Array.isArray(response) ? response : [response];
    return rows
      .map((row) => ({
        open: Number(row.open),
        high: Number(row.high),
        low: Number(row.low),
        close: Number(row.close)
      }))
      .filter((point) =>
        Number.isFinite(point.open)
          && Number.isFinite(point.high)
          && Number.isFinite(point.low)
          && Number.isFinite(point.close)
      )
      .slice(-50);
  }
}