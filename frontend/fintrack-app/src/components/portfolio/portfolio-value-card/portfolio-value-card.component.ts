import { Component, OnInit } from '@angular/core';
import { forkJoin } from 'rxjs';
import { PortfolioService } from '../../../app/services/portfolio.service';

@Component({
  selector: 'app-portfolio-value-card',
  imports: [],
  templateUrl: './portfolio-value-card.component.html',
  styleUrl: './portfolio-value-card.component.css'
})
export class PortfolioValueCard implements OnInit {
  private readonly defaultSymbol = 'BTCUSDT'

  totalPortfolioValue = 0
  totalPortfolioPercentage = 0

  constructor(private portfolioService: PortfolioService) {}

  ngOnInit(): void {
    this.loadPortfolioMetrics()
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
    return `${sign}${this.totalPortfolioPercentage.toFixed(2)}% (24h)`
  }

  private loadPortfolioMetrics(): void {
    forkJoin({
      value: this.portfolioService.calculateProfitValue(this.defaultSymbol),
      percentage: this.portfolioService.calculateProfitPercentage(this.defaultSymbol)
    }).subscribe({
      next: ({ value, percentage }) => {
        this.totalPortfolioValue = this.parseNumeric(value.value)
        this.totalPortfolioPercentage = this.parseNumeric(percentage.value)
      },
      error: () => {
        this.totalPortfolioValue = 0
        this.totalPortfolioPercentage = 0
      }
    })
  }

  private parseNumeric(value: number | string): number {
    const parsed = typeof value === 'number' ? value : Number(value)
    return Number.isFinite(parsed) ? parsed : 0
  }
}
