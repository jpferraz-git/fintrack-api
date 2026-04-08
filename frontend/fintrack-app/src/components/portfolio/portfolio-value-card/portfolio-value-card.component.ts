import { CommonModule } from '@angular/common';
import { Component, Input, OnChanges, OnDestroy, SimpleChanges } from '@angular/core';
import {
  BehaviorSubject,
  Subject,
  catchError,
  combineLatest,
  forkJoin,
  map,
  of,
  startWith,
  switchMap,
  throttleTime
} from 'rxjs';
import { MarketDataService, MarketKlinesResponse } from '../../../app/services/market-data.service';
import {
  PortfolioAssetResponse,
  PortfolioService,
  PortfolioTransactionResponse
} from '../../../app/services/portfolio.service';

type PortfolioChartRange = '1D' | '1W' | '1M' | 'ALL'

interface PortfolioRangeOption {
  value: PortfolioChartRange
  label: string
}

interface PortfolioMetricsViewModel {
  isLoading: boolean
  hasError: boolean
  totalPortfolioValue: number
  totalPortfolioPercentage: number
  formattedTotalPortfolioValue: string
  formattedTotalPortfolioPercentage: string
}

interface PortfolioChartViewModel {
  isLoading: boolean
  hasError: boolean
  hasData: boolean
  linePath: string
  areaPath: string
}

interface SymbolPriceSeries {
  symbol: string
  series: PricePoint[]
}

interface PricePoint {
  timestamp: number
  close: number
}

interface SignedTransaction {
  timestamp: number
  quantity: number
}

interface PortfolioValuePoint {
  timestamp: number
  value: number
}

@Component({
  selector: 'app-portfolio-value-card',
  imports: [CommonModule],
  templateUrl: './portfolio-value-card.component.html',
  styleUrl: './portfolio-value-card.component.css'
})
export class PortfolioValueCard implements OnChanges, OnDestroy {
  @Input() refreshTrigger = 0

  selectedRange: PortfolioChartRange = '1W'
  readonly chartGradientId = `portfolioArea-${Math.random().toString(36).slice(2, 10)}`
  readonly rangeOptions: PortfolioRangeOption[] = [
    { value: '1D', label: '1D' },
    { value: '1W', label: '1W' },
    { value: '1M', label: '1M' },
    { value: 'ALL', label: 'All' }
  ]

  private readonly refreshRequest$ = new Subject<void>()
  private readonly rangeSelection$ = new BehaviorSubject<PortfolioChartRange>(this.selectedRange)

  readonly metricsState$ = this.refreshRequest$.pipe(
    startWith(void 0),
    switchMap(() =>
      forkJoin({
        value: this.portfolioService.calculateTotalProfitValue(),
        percentage: this.portfolioService.calculateTotalProfitPercentage()
      }).pipe(
        map(({ value, percentage }) => {
          const totalPortfolioValue = this.parseNumeric(value.value)
          const totalPortfolioPercentage = this.parseNumeric(percentage.value)

          return this.buildViewModel({
            isLoading: false,
            hasError: false,
            totalPortfolioValue,
            totalPortfolioPercentage
          })
        }),
        catchError(() =>
          of(this.buildViewModel({
            isLoading: false,
            hasError: true,
            totalPortfolioValue: 0,
            totalPortfolioPercentage: 0
          }))
        ),
        startWith(this.buildViewModel({
          isLoading: true,
          hasError: false,
          totalPortfolioValue: 0,
          totalPortfolioPercentage: 0
        }))
      )
    )
  )

  readonly chartState$ = combineLatest([
    this.refreshRequest$.pipe(
      startWith(void 0),
      throttleTime(30000, undefined, { leading: true, trailing: true })
    ),
    this.rangeSelection$
  ]).pipe(
    switchMap(([, range]) => this.loadChartState(range))
  )

  constructor(
    private portfolioService: PortfolioService,
    private marketDataService: MarketDataService
  ) {}

  ngOnDestroy(): void {
    this.refreshRequest$.complete()
    this.rangeSelection$.complete()
  }

  ngOnChanges(changes: SimpleChanges): void {
    const refreshTriggerChange = changes['refreshTrigger']
    if (refreshTriggerChange && !refreshTriggerChange.firstChange) {
      this.refreshRequest$.next()
    }
  }

  onRangeSelected(range: PortfolioChartRange): void {
    if (this.selectedRange === range) {
      return
    }

    this.selectedRange = range
    this.rangeSelection$.next(range)
  }

  isRangeActive(range: PortfolioChartRange): boolean {
    return this.selectedRange === range
  }

  private loadChartState(range: PortfolioChartRange) {
    const config = this.resolveRangeConfig(range)

    return forkJoin({
      assets: this.portfolioService.getAssets(),
      transactions: this.portfolioService.getTransactions()
    }).pipe(
      switchMap(({ assets, transactions }) => {
        const trackedSymbols = [...new Set(assets.map((asset) => asset.symbol))]
        if (trackedSymbols.length === 0) {
          return of(this.emptyChartState(false, false))
        }

        const klineRequests = trackedSymbols.map((symbol) =>
          this.marketDataService.getKlines(symbol, config.interval, config.limit).pipe(
            map((response) => ({ symbol, series: this.toPriceSeries(response) })),
            catchError(() => of({ symbol, series: [] as PricePoint[] }))
          )
        )

        return forkJoin(klineRequests).pipe(
          map((seriesBySymbol) =>
            this.buildChartStateFromHistory(assets, transactions, seriesBySymbol)
          )
        )
      }),
      catchError(() => of(this.emptyChartState(false, true))),
      startWith(this.emptyChartState(true, false))
    )
  }

  private buildChartStateFromHistory(
    assets: PortfolioAssetResponse[],
    transactions: PortfolioTransactionResponse[],
    seriesBySymbol: SymbolPriceSeries[]
  ): PortfolioChartViewModel {
    const valueSeries = this.buildPortfolioValueSeries(assets, transactions, seriesBySymbol)
    if (valueSeries.length < 2) {
      return this.emptyChartState(false, false)
    }

    const geometry = this.buildChartGeometry(valueSeries)
    return {
      isLoading: false,
      hasError: false,
      hasData: true,
      linePath: geometry.linePath,
      areaPath: geometry.areaPath
    }
  }

  private buildPortfolioValueSeries(
    assets: PortfolioAssetResponse[],
    transactions: PortfolioTransactionResponse[],
    seriesBySymbol: SymbolPriceSeries[]
  ): PortfolioValuePoint[] {
    const symbols = [...new Set(assets.map((asset) => asset.symbol))]
    const currentQuantityBySymbol = new Map<string, number>()

    assets.forEach((asset) => {
      const current = currentQuantityBySymbol.get(asset.symbol) ?? 0
      currentQuantityBySymbol.set(asset.symbol, current + this.parseNumeric(asset.quantity))
    })

    const priceSeriesMap = new Map<string, PricePoint[]>(
      seriesBySymbol.map((entry) => [entry.symbol, entry.series])
    )

    const timeline = [...new Set(
      seriesBySymbol.flatMap((entry) => entry.series.map((point) => point.timestamp))
    )].sort((first, second) => first - second)

    if (timeline.length === 0) {
      return []
    }

    const rangeStart = timeline[0]
    const transactionsBySymbol = this.groupTransactionsBySymbol(transactions)

    const quantityBySymbol = new Map<string, number>()
    const transactionCursorBySymbol = new Map<string, number>()
    const transactionsInRangeBySymbol = new Map<string, SignedTransaction[]>()
    const priceCursorBySymbol = new Map<string, number>()

    symbols.forEach((symbol) => {
      const symbolTransactions = transactionsBySymbol.get(symbol) ?? []
      const transactionsInRange = symbolTransactions.filter((transaction) => transaction.timestamp >= rangeStart)
      const netAfterRangeStart = transactionsInRange.reduce((acc, transaction) => acc + transaction.quantity, 0)

      const startQuantity = Math.max((currentQuantityBySymbol.get(symbol) ?? 0) - netAfterRangeStart, 0)

      quantityBySymbol.set(symbol, startQuantity)
      transactionCursorBySymbol.set(symbol, 0)
      transactionsInRangeBySymbol.set(symbol, transactionsInRange)
      priceCursorBySymbol.set(symbol, -1)
    })

    return timeline.map((timestamp) => {
      let portfolioValue = 0

      symbols.forEach((symbol) => {
        const symbolTransactions = transactionsInRangeBySymbol.get(symbol) ?? []
        let transactionCursor = transactionCursorBySymbol.get(symbol) ?? 0
        let quantity = quantityBySymbol.get(symbol) ?? 0

        while (
          transactionCursor < symbolTransactions.length
          && symbolTransactions[transactionCursor].timestamp <= timestamp
        ) {
          quantity += symbolTransactions[transactionCursor].quantity
          transactionCursor += 1
        }

        quantity = Math.max(quantity, 0)

        transactionCursorBySymbol.set(symbol, transactionCursor)
        quantityBySymbol.set(symbol, quantity)

        const prices = priceSeriesMap.get(symbol) ?? []
        let priceCursor = priceCursorBySymbol.get(symbol) ?? -1

        while (priceCursor + 1 < prices.length && prices[priceCursor + 1].timestamp <= timestamp) {
          priceCursor += 1
        }

        priceCursorBySymbol.set(symbol, priceCursor)

        const price = priceCursor >= 0 ? prices[priceCursor].close : 0
        portfolioValue += quantity * price
      })

      return {
        timestamp,
        value: Number.isFinite(portfolioValue) ? portfolioValue : 0
      }
    })
  }

  private buildChartGeometry(series: PortfolioValuePoint[]): { linePath: string; areaPath: string } {
    const width = 780
    const chartTop = 8
    const chartBottom = 250
    const usableHeight = chartBottom - chartTop

    const minValue = Math.min(...series.map((point) => point.value))
    const maxValue = Math.max(...series.map((point) => point.value))

    const minimumRange = Math.max(Math.abs(maxValue) * 0.02, 1)
    const normalizedMin = minValue
    const normalizedMax = maxValue - minValue < minimumRange ? minValue + minimumRange : maxValue
    const valueRange = Math.max(normalizedMax - normalizedMin, 1)

    const toX = (index: number): number => {
      if (series.length <= 1) {
        return 0
      }

      return (index / (series.length - 1)) * width
    }

    const toY = (value: number): number => {
      const relative = (value - normalizedMin) / valueRange
      return chartBottom - relative * usableHeight
    }

    const linePath = series
      .map((point, index) => {
        const command = index === 0 ? 'M' : 'L'
        return `${command}${toX(index).toFixed(2)},${toY(point.value).toFixed(2)}`
      })
      .join(' ')

    const lastX = toX(series.length - 1).toFixed(2)
    const bottomY = chartBottom.toFixed(2)
    const areaPath = `${linePath} L${lastX},${bottomY} L0,${bottomY} Z`

    return { linePath, areaPath }
  }

  private groupTransactionsBySymbol(transactions: PortfolioTransactionResponse[]): Map<string, SignedTransaction[]> {
    const grouped = new Map<string, SignedTransaction[]>()

    transactions.forEach((transaction) => {
      const timestamp = this.parseTimestamp(transaction.createdAt)
      const quantity = this.parseNumeric(transaction.quantity)

      if (!Number.isFinite(timestamp) || timestamp <= 0 || quantity <= 0) {
        return
      }

      const signedQuantity = transaction.type === 'SELL' ? -quantity : quantity
      const existing = grouped.get(transaction.symbol) ?? []
      existing.push({
        timestamp,
        quantity: signedQuantity
      })
      grouped.set(transaction.symbol, existing)
    })

    grouped.forEach((symbolTransactions) => {
      symbolTransactions.sort((first, second) => first.timestamp - second.timestamp)
    })

    return grouped
  }

  private toPriceSeries(response: MarketKlinesResponse): PricePoint[] {
    const rows = Array.isArray(response) ? response : [response]

    return rows
      .map((row) => ({
        timestamp: this.parseTimestamp(row.closeTime || row.openTime),
        close: this.parseNumeric(row.close)
      }))
      .filter((point) => Number.isFinite(point.timestamp) && point.timestamp > 0 && point.close > 0)
      .sort((first, second) => first.timestamp - second.timestamp)
  }

  private resolveRangeConfig(range: PortfolioChartRange): { interval: string; limit: number } {
    if (range === '1D') {
      return { interval: '15m', limit: 96 }
    }

    if (range === '1W') {
      return { interval: '1h', limit: 168 }
    }

    if (range === '1M') {
      return { interval: '4h', limit: 180 }
    }

    return { interval: '1d', limit: 1000 }
  }

  private emptyChartState(isLoading: boolean, hasError: boolean): PortfolioChartViewModel {
    return {
      isLoading,
      hasError,
      hasData: false,
      linePath: '',
      areaPath: ''
    }
  }

  private parseNumeric(value: number | string): number {
    const parsed = typeof value === 'number' ? value : Number(value)
    return Number.isFinite(parsed) ? parsed : 0
  }

  private parseTimestamp(value: string | number): number {
    const numeric = typeof value === 'number' ? value : Number(value)
    if (Number.isFinite(numeric)) {
      return numeric
    }

    const parsedDate = Date.parse(String(value))
    return Number.isFinite(parsedDate) ? parsedDate : 0
  }

  private buildViewModel(state: {
    isLoading: boolean
    hasError: boolean
    totalPortfolioValue: number
    totalPortfolioPercentage: number
  }): PortfolioMetricsViewModel {
    const formattedTotalPortfolioValue = new Intl.NumberFormat('en-US', {
      style: 'currency',
      currency: 'USD',
      minimumFractionDigits: 2,
      maximumFractionDigits: 2
    }).format(state.totalPortfolioValue)

    const sign = state.totalPortfolioPercentage > 0 ? '+' : ''
    const formattedTotalPortfolioPercentage = `${sign}${state.totalPortfolioPercentage.toFixed(2)}%`

    return {
      ...state,
      formattedTotalPortfolioValue,
      formattedTotalPortfolioPercentage
    }
  }
}
