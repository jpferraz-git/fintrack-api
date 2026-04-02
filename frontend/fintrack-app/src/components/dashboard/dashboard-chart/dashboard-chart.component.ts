import { NgFor } from '@angular/common';
import { Component } from '@angular/core';

type CandlePoint = {
  open: number;
  high: number;
  low: number;
  close: number;
};

type RenderCandle = {
  trend: 'up' | 'down';
  wickTop: string;
  wickBottom: string;
  bodyTop: string;
  bodyHeight: string;
};

@Component({
  selector: 'app-dashboard-chart',
  imports: [NgFor],
  templateUrl: './dashboard-chart.component.html',
  styleUrl: './dashboard-chart.component.css'
})
export class DashboardChart {
  private readonly ohlcSeries: CandlePoint[] = [
    { open: 67320, high: 67610, low: 66980, close: 67480 },
    { open: 67480, high: 67890, low: 67320, close: 67730 },
    { open: 67730, high: 67910, low: 67280, close: 67390 },
    { open: 67390, high: 68120, low: 67120, close: 67980 },
    { open: 67980, high: 68240, low: 67620, close: 67710 },
    { open: 67710, high: 68450, low: 67590, close: 68270 },
    { open: 68270, high: 68620, low: 68010, close: 68120 },
    { open: 68120, high: 68790, low: 67980, close: 68640 },
    { open: 68640, high: 68950, low: 68310, close: 68470 },
    { open: 68470, high: 69210, low: 68280, close: 69080 },
    { open: 69080, high: 69420, low: 68810, close: 69160 },
    { open: 69160, high: 69680, low: 68920, close: 69410 }
  ];

  readonly candles: RenderCandle[] = this.buildCandles(this.ohlcSeries);

  trackByIndex(index: number): number {
    return index;
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
        trend: point.close >= point.open ? 'up' : 'down',
        wickTop: fromTop(point.high),
        wickBottom: `${toPercent(point.low)}%`,
        bodyTop: fromTop(upperBody),
        bodyHeight: `${Math.max(bodySize, 2)}%`
      };
    });
  }
}