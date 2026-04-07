import { CommonModule } from '@angular/common';
import { Component, Input, OnChanges, OnDestroy, OnInit, SimpleChanges } from '@angular/core';
import { Subject, takeUntil } from 'rxjs';
import { PortfolioService, PortfolioTransactionResponse } from '../../../app/services/portfolio.service';

@Component({
  selector: 'app-portfolio-transactions-table',
  imports: [CommonModule],
  templateUrl: './portfolio-transactions-table.component.html',
  styleUrl: './portfolio-transactions-table.component.css'
})
export class PortfolioTransactionsTable implements OnInit, OnChanges, OnDestroy {
  @Input() refreshTrigger = 0;

  transactions: PortfolioTransactionResponse[] = [];
  isTransactionsLoading = true;
  hasTransactionsError = false;

  private readonly assetNames: Record<string, string> = {
    BTC: 'Bitcoin',
    ETH: 'Ethereum',
    SOL: 'Solana',
    BNB: 'BNB',
    XRP: 'XRP',
    ADA: 'Cardano',
    DOGE: 'Dogecoin',
    AVAX: 'Avalanche',
    DOT: 'Polkadot',
    LINK: 'Chainlink',
    LTC: 'Litecoin',
    TRX: 'TRON'
  };

  private readonly destroy$ = new Subject<void>();

  constructor(private portfolioService: PortfolioService) {}

  ngOnInit(): void {
    this.loadTransactions();
  }

  ngOnChanges(changes: SimpleChanges): void {
    const refreshTriggerChange = changes['refreshTrigger'];
    if (refreshTriggerChange && !refreshTriggerChange.firstChange) {
      this.loadTransactions();
    }
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  trackByTransactionId(index: number, transaction: PortfolioTransactionResponse): string {
    return transaction.id;
  }

  getAssetTicker(symbol: string): string {
    return symbol.endsWith('USDT') ? symbol.slice(0, -4) : symbol;
  }

  getAssetName(symbol: string): string {
    const ticker = this.getAssetTicker(symbol);
    return this.assetNames[ticker] ?? ticker;
  }

  getAssetAvatar(symbol: string): string {
    const ticker = this.getAssetTicker(symbol);
    return ticker.charAt(0);
  }

  getAssetAvatarClass(symbol: string): string {
    const ticker = this.getAssetTicker(symbol).toLowerCase();
    return `portfolio-asset-avatar--${ticker}`;
  }

  formatTransactionType(type: string): string {
    return type === 'SELL' ? 'SELL' : 'BUY';
  }

  formatQuantity(value: number | string): string {
    const quantity = this.parseNumeric(value);
    return quantity.toLocaleString('en-US', {
      minimumFractionDigits: 2,
      maximumFractionDigits: 8
    });
  }

  formatUsd(value: number | string): string {
    const parsedValue = this.parseNumeric(value);
    return new Intl.NumberFormat('en-US', {
      style: 'currency',
      currency: 'USD',
      minimumFractionDigits: 2,
      maximumFractionDigits: 2
    }).format(parsedValue);
  }

  formatDate(dateValue: string): string {
    const date = new Date(dateValue);
    if (Number.isNaN(date.getTime())) {
      return '--';
    }

    return date.toLocaleString('pt-BR', {
      day: '2-digit',
      month: '2-digit',
      year: 'numeric',
      hour: '2-digit',
      minute: '2-digit'
    });
  }

  formatDatePart(dateValue: string): string {
    const date = new Date(dateValue);
    if (Number.isNaN(date.getTime())) {
      return '--';
    }

    return date.toLocaleDateString('pt-BR', {
      day: '2-digit',
      month: '2-digit',
      year: 'numeric'
    });
  }

  formatTimePart(dateValue: string): string {
    const date = new Date(dateValue);
    if (Number.isNaN(date.getTime())) {
      return '--';
    }

    return date.toLocaleTimeString('pt-BR', {
      hour: '2-digit',
      minute: '2-digit'
    });
  }

  getTransactionStatus(transaction: PortfolioTransactionResponse): 'PENDING' | 'COMPLETED' {
    const createdAt = new Date(transaction.createdAt).getTime();
    if (!Number.isFinite(createdAt)) {
      return 'COMPLETED';
    }

    const elapsed = Date.now() - createdAt;
    return elapsed < 10 * 60 * 1000 ? 'PENDING' : 'COMPLETED';
  }

  isPending(transaction: PortfolioTransactionResponse): boolean {
    return this.getTransactionStatus(transaction) === 'PENDING';
  }

  formatQuantityWithTicker(transaction: PortfolioTransactionResponse): string {
    return `${this.formatQuantity(transaction.quantity)} ${this.getAssetTicker(transaction.symbol)}`;
  }

  formatTotal(transaction: PortfolioTransactionResponse): string {
    const quantity = this.parseNumeric(transaction.quantity);
    const price = this.parseNumeric(transaction.price);
    return this.formatUsd(quantity * price);
  }

  private loadTransactions(): void {
    this.isTransactionsLoading = true;
    this.hasTransactionsError = false;

    this.portfolioService
      .getTransactions()
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (transactions: PortfolioTransactionResponse[]) => {
          this.transactions = [...transactions].sort((a, b) => {
            const first = new Date(a.createdAt).getTime();
            const second = new Date(b.createdAt).getTime();
            return second - first;
          });
          this.isTransactionsLoading = false;
        },
        error: () => {
          this.transactions = [];
          this.hasTransactionsError = true;
          this.isTransactionsLoading = false;
        }
      });
  }

  private parseNumeric(value: number | string): number {
    const parsed = typeof value === 'number' ? value : Number(value);
    return Number.isFinite(parsed) ? parsed : 0;
  }
}
