import { CommonModule } from '@angular/common';
import { Component, Input, OnChanges, OnDestroy, SimpleChanges } from '@angular/core';
import { Subject, catchError, forkJoin, map, of, startWith, switchMap } from 'rxjs';
import { PortfolioService } from '../../../app/services/portfolio.service';

interface PortfolioMetricsViewModel {
  isLoading: boolean
  hasError: boolean
  totalPortfolioValue: number
  totalPortfolioPercentage: number
  formattedTotalPortfolioValue: string
  formattedTotalPortfolioPercentage: string
}

@Component({
  selector: 'app-portfolio-value-card',
  imports: [CommonModule],
  templateUrl: './portfolio-value-card.component.html',
  styleUrl: './portfolio-value-card.component.css'
})
export class PortfolioValueCard implements OnChanges, OnDestroy {
  @Input() refreshTrigger = 0

  private readonly refreshRequest$ = new Subject<void>()

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

  constructor(private portfolioService: PortfolioService) {}

  ngOnDestroy(): void {
    this.refreshRequest$.complete()
  }

  ngOnChanges(changes: SimpleChanges): void {
    const refreshTriggerChange = changes['refreshTrigger']
    if (refreshTriggerChange && !refreshTriggerChange.firstChange) {
      this.refreshRequest$.next()
    }
  }

  private parseNumeric(value: number | string): number {
    const parsed = typeof value === 'number' ? value : Number(value)
    return Number.isFinite(parsed) ? parsed : 0
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
