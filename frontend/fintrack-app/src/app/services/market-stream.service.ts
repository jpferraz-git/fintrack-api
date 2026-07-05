import { Injectable, OnDestroy } from '@angular/core';
import { BehaviorSubject, Observable, Subscription, timer, forkJoin, of } from 'rxjs';
import { catchError, exhaustMap } from 'rxjs/operators';
import { MarketDataService, MarketIndividualPrice, MarketKlinesResponse, Market24hTickerResponse } from './market-data.service';

export interface MarketStreamData {
    price: MarketIndividualPrice | null;
    klines: MarketKlinesResponse | null;
    ticker24h: Market24hTickerResponse | null;
}

@Injectable({
  providedIn: 'root'
})
export class MarketStreamService implements OnDestroy {
  private streams = new Map<string, BehaviorSubject<MarketStreamData | null>>();
  private subscriptions = new Map<string, Subscription>();

  constructor(private marketDataService: MarketDataService) {}

  getStream(symbol: string): Observable<MarketStreamData | null> {
    if (!this.streams.has(symbol)) {
      this.streams.set(symbol, new BehaviorSubject<MarketStreamData | null>(null));
      this.startPolling(symbol);
    }
    return this.streams.get(symbol)!.asObservable();
  }

  private startPolling(symbol: string): void {
    const sub = timer(0, 2000).pipe(
      exhaustMap(() => forkJoin({
        price: this.marketDataService.getPrice(symbol).pipe(catchError(() => of(null))),
        klines: this.marketDataService.getKlines(symbol, '1m').pipe(catchError(() => of(null))),
        ticker24h: this.marketDataService.get24hTicker(symbol).pipe(catchError(() => of(null)))
      }))
    ).subscribe(data => {
      this.streams.get(symbol)!.next(data);
    });

    this.subscriptions.set(symbol, sub);
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => sub.unsubscribe());
    this.subscriptions.clear();
  }
}
