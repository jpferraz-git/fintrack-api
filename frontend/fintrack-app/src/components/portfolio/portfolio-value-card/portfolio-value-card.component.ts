import { CommonModule } from '@angular/common';
import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { forkJoin } from 'rxjs';
import { PortfolioService } from '../../../app/services/portfolio.service';

@Component({
  selector: 'app-portfolio-value-card',
  imports: [CommonModule],
  templateUrl: './portfolio-value-card.component.html',
  styleUrl: './portfolio-value-card.component.css'
})
export class PortfolioValueCard implements OnInit, OnChanges {
  @Input() refreshTrigger = 0

  totalPortfolioValue = 0
  totalPortfolioPercentage = 0
  isLoading = true
  hasError = false

  constructor(private portfolioService: PortfolioService) {}

  ngOnInit(): void {
    this.loadPortfolioMetrics()
  }

  ngOnChanges(changes: SimpleChanges): void {
    const refreshTriggerChange = changes['refreshTrigger']
    if (refreshTriggerChange && !refreshTriggerChange.firstChange) {
      this.loadPortfolioMetrics()
    }
  }

  get formattedTotalPortfolioValue(): string {
    return new Intl.NumberFormat('en-US', {
      style: 'currency',
      currency: 'USD',
      minimumFractionDigits: 2,
      maximumFractionDigits: 2
    }).format(this.totalPortfolioValue)
  }

  get formattedTotalPortfolioPercentage(): string {
    const sign = this.totalPortfolioPercentage > 0 ? '+' : ''
    return `${sign}${this.totalPortfolioPercentage.toFixed(2)}%`
  }

  private loadPortfolioMetrics(): void {
    this.isLoading = true
    this.hasError = false

    forkJoin({
      value: this.portfolioService.calculateTotalProfitValue(),
      percentage: this.portfolioService.calculateTotalProfitPercentage()
    }).subscribe({
      next: ({ value, percentage }) => {
        this.totalPortfolioValue = this.parseNumeric(value.value)
        this.totalPortfolioPercentage = this.parseNumeric(percentage.value)
        this.isLoading = false
      },
      error: () => {
        this.totalPortfolioValue = 0
        this.totalPortfolioPercentage = 0
        this.hasError = true
        this.isLoading = false
      }
    })
  }

  private parseNumeric(value: number | string): number {
    const parsed = typeof value === 'number' ? value : Number(value)
    return Number.isFinite(parsed) ? parsed : 0
  }
}
