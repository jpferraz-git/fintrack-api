import { NgFor, NgIf } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, EventEmitter, Input, OnChanges, OnDestroy, Output, SimpleChanges } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { Subject, takeUntil } from 'rxjs';
import { MarketDataService } from '../../../app/services/market-data.service';
import { PortfolioAssetResponse, PortfolioService } from '../../../app/services/portfolio.service';
import { UtilsService } from '../../../app/services/utils.service';

interface SellOption {
  label: string
  symbol: string
  availableQuantity: number
}

@Component({
  selector: 'app-sell-asset-modal',
  imports: [NgIf, NgFor, FormsModule],
  templateUrl: './sell-asset-modal.component.html',
  styleUrl: './sell-asset-modal.component.css'
})
export class SellAssetModal implements OnChanges, OnDestroy {
  @Input() open = false;
  @Output() closed = new EventEmitter<void>();
  @Output() assetSold = new EventEmitter<void>();

  sellOptions: SellOption[] = [];
  selectedSymbol = '';
  sellQuantity: number | null = null;
  availableQuantity = 0;
  currentPrice: number | null = null;

  isSubmitting = false;
  isLoadingPrice = false;
  isLoadingAssets = false;
  submitError = '';

  private readonly destroy$ = new Subject<void>();

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
    XRP: 'Ripple',
    SOL: 'Solana',
    TRX: 'TRON'
  };

  constructor(
    private portfolioService: PortfolioService,
    private marketDataService: MarketDataService,
    private utilsService: UtilsService
  ) {}

  ngOnChanges(changes: SimpleChanges): void {
    const openChange = changes['open'];
    if (openChange?.currentValue === true) {
      this.loadSellOptions();
    }
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  close(): void {
    this.resetTransientState();
    this.closed.emit();
  }

  onBackdropClick(event: MouseEvent): void {
    if (event.target === event.currentTarget) {
      this.close();
    }
  }

  onSymbolChange(symbol: string): void {
    this.selectedSymbol = symbol;
    this.availableQuantity = this.findAvailableQuantity(symbol);
    this.loadCurrentPrice();
  }

  sellFromPortfolio(form: NgForm): void {
    if (form.invalid || this.isSubmitting || this.isLoadingAssets || !this.hasSellableAssets) {
      return;
    }

    const normalizedSellQuantity = Number(this.sellQuantity);
    if (!Number.isFinite(normalizedSellQuantity) || normalizedSellQuantity <= 0) {
      this.submitError = 'Sell quantity must be greater than zero.';
      return;
    }

    if (normalizedSellQuantity > this.availableQuantity) {
      this.submitError = `Sell quantity exceeds available holdings (${this.formattedAvailableQuantity}).`;
      return;
    }

    if (!this.currentPrice || !Number.isFinite(this.currentPrice) || this.currentPrice <= 0) {
      this.submitError = 'Could not load current market price. Please try again.';
      return;
    }

    this.isSubmitting = true;
    this.submitError = '';

    this.portfolioService
      .createTransaction({
        fkUser: null,
        symbol: this.selectedSymbol,
        type: 'SELL',
        quantity: Number(normalizedSellQuantity.toFixed(8)),
        price: this.currentPrice
      })
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: () => {
          this.isSubmitting = false;
          this.assetSold.emit();
          form.resetForm();
          this.resetTransientState();
          this.closed.emit();
        },
        error: (error: HttpErrorResponse) => {
          this.isSubmitting = false;
          this.submitError = this.resolveErrorMessage(error);
        }
      });
  }

  get hasSellableAssets(): boolean {
    return this.sellOptions.length > 0;
  }

  get assetCode(): string {
    return this.selectedSymbol.replace('USDT', '');
  }

  get formattedCurrentPrice(): string {
    if (!this.currentPrice || this.currentPrice <= 0) {
      return '--';
    }

    return this.utilsService.formatUsd(this.currentPrice);
  }

  get formattedEstimatedTotal(): string {
    if (!this.currentPrice || !this.sellQuantity || this.sellQuantity <= 0) {
      return '--';
    }

    const estimate = Number(this.sellQuantity) * this.currentPrice;
    return this.utilsService.formatUsd(estimate);
  }

  get formattedAvailableQuantity(): string {
    const formattedQuantity = this.utilsService.formatQuantity(this.availableQuantity, {
      minimumFractionDigits: 0
    });
    return `${formattedQuantity} ${this.assetCode}`;
  }

  formatQuantity(value: number): string {
    return this.utilsService.formatQuantity(value, {
      minimumFractionDigits: 0
    });
  }

  private loadSellOptions(): void {
    this.isLoadingAssets = true;
    this.submitError = '';

    this.portfolioService
      .getAssets()
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (assets: PortfolioAssetResponse[]) => {
          this.sellOptions = assets
            .map((asset) => {
              const availableQuantity = this.utilsService.parseNumeric(asset.quantity);
              const assetCode = asset.symbol.replace('USDT', '');
              const assetName = this.assetNames[assetCode] ?? assetCode;

              return {
                symbol: asset.symbol,
                availableQuantity,
                label: `${assetName} (${asset.symbol})`
              };
            })
            .filter((option) => option.availableQuantity > 0)
            .sort((a, b) => a.label.localeCompare(b.label));

          if (this.sellOptions.length === 0) {
            this.selectedSymbol = '';
            this.availableQuantity = 0;
            this.currentPrice = null;
            this.isLoadingPrice = false;
          } else {
            const existingSelection = this.sellOptions.find((option) => option.symbol === this.selectedSymbol);
            const nextSelection = existingSelection?.symbol ?? this.sellOptions[0].symbol;
            this.selectedSymbol = nextSelection;
            this.availableQuantity = this.findAvailableQuantity(nextSelection);
            this.loadCurrentPrice();
          }

          this.isLoadingAssets = false;
        },
        error: () => {
          this.sellOptions = [];
          this.selectedSymbol = '';
          this.availableQuantity = 0;
          this.currentPrice = null;
          this.isLoadingAssets = false;
          this.isLoadingPrice = false;
          this.submitError = 'Could not load portfolio assets. Please try again.';
        }
      });
  }

  private loadCurrentPrice(): void {
    if (!this.selectedSymbol) {
      this.currentPrice = null;
      this.isLoadingPrice = false;
      return;
    }

    this.isLoadingPrice = true;

    this.marketDataService
      .getPrice(this.selectedSymbol)
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (priceResponse) => {
          this.currentPrice = this.utilsService.parseNumeric(priceResponse.price);
          this.isLoadingPrice = false;
        },
        error: () => {
          this.currentPrice = null;
          this.isLoadingPrice = false;
        }
      });
  }

  private findAvailableQuantity(symbol: string): number {
    const option = this.sellOptions.find((candidate) => candidate.symbol === symbol);
    return option ? option.availableQuantity : 0;
  }
  private resolveErrorMessage(error: HttpErrorResponse): string {
    const rawError = error.error as { message?: string } | string | null;
    if (rawError && typeof rawError === 'object' && rawError.message) {
      return rawError.message;
    }

    if (typeof rawError === 'string' && rawError.trim()) {
      return rawError;
    }

    return 'Could not register sell transaction. Please try again.';
  }

  private resetTransientState(): void {
    this.isSubmitting = false;
    this.isLoadingAssets = false;
    this.isLoadingPrice = false;
    this.submitError = '';
    this.sellOptions = [];
    this.selectedSymbol = '';
    this.sellQuantity = null;
    this.availableQuantity = 0;
    this.currentPrice = null;
  }
}
