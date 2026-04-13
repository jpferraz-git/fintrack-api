import { CommonModule } from '@angular/common';
import { Component, Input, OnChanges, OnDestroy, OnInit, SimpleChanges } from '@angular/core';
import { Subject, catchError, map, merge, of, switchMap, takeUntil, timer } from 'rxjs';
import { PortfolioService, PortfolioTransactionResponse } from '../../../app/services/portfolio.service';
import { UtilsService } from '../../../app/services/utils.service';

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
    AVAX: 'Avalanche',
    BNB: 'BNB',
    BTC: 'Bitcoin',
    ADA: 'Cardano',
    LINK: 'Chainlink',
    DOGE: 'Dogecoin',
    ETH: 'Ethereum',
    LTC: 'Litecoin',
    DOT: 'Polkadot',
    SOL: 'Solana',
    TRX: 'TRON',
    XRP: 'XRP'
  };

  private readonly coinIconByCode: Record<string, string> = {
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

  private readonly destroy$ = new Subject<void>();
  private readonly manualRefresh$ = new Subject<void>();

  constructor(
    private portfolioService: PortfolioService,
    private utilsService: UtilsService
  ) {}

  ngOnInit(): void {
    this.startPolling();
  }

  ngOnChanges(changes: SimpleChanges): void {
    const refreshTriggerChange = changes['refreshTrigger'];
    if (refreshTriggerChange && !refreshTriggerChange.firstChange) {
      this.manualRefresh$.next();
    }
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
    this.manualRefresh$.complete();
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

  getAssetIcon(symbol: string): string {
    const ticker = this.getAssetTicker(symbol);
    return this.coinIconByCode[ticker] ?? ticker.toLowerCase();
  }

  formatTransactionType(type: string): string {
    return type === 'SELL' ? 'SELL' : 'BUY';
  }

  formatQuantity(value: number | string): string {
    return this.utilsService.formatQuantity(value, {
      minimumFractionDigits: 2
    });
  }

  formatUsd(value: number | string): string {
    return this.utilsService.formatUsd(value);
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
    const quantity = this.utilsService.parseNumeric(transaction.quantity);
    const price = this.utilsService.parseNumeric(transaction.price);
    return this.formatUsd(quantity * price);
  }

  private startPolling(): void {
    merge(timer(0, 2000), this.manualRefresh$)
      .pipe(
        switchMap(() =>
          this.portfolioService.getTransactions().pipe(
            map((transactions) =>
              [...transactions].sort((a, b) => {
                const first = new Date(a.createdAt).getTime();
                const second = new Date(b.createdAt).getTime();
                return second - first;
              })
            ),
            catchError(() => of(null))
          )
        ),
        takeUntil(this.destroy$)
      )
      .subscribe((transactions) => {
        this.isTransactionsLoading = false;

        if (!transactions) {
          this.transactions = [];
          this.hasTransactionsError = true;
          return;
        }

        this.transactions = transactions;
        this.hasTransactionsError = false;
      });
  }
}
