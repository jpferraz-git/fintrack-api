import { NgFor, NgIf } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, EventEmitter, Input, OnChanges, Output, SimpleChanges } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { MarketDataService } from '../../../app/services/market-data.service';
import { PortfolioService } from '../../../app/services/portfolio.service';

interface CryptoOption {
  label: string
  symbol: string
}

@Component({
  selector: 'app-add-asset-modal',
  imports: [NgIf, NgFor, FormsModule],
  templateUrl: './add-asset-modal.component.html',
  styleUrl: './add-asset-modal.component.css'
})
export class AddAssetModal implements OnChanges {
  @Input() open = false;
  @Output() closed = new EventEmitter<void>();
  @Output() assetCreated = new EventEmitter<void>();

  private readonly marketOverviewCryptos = [
    { symbol: 'AVAXUSDT', coinName: 'Avalanche' },
    { symbol: 'BNBUSDT', coinName: 'BNB' },
    { symbol: 'BTCUSDT', coinName: 'Bitcoin' },
    { symbol: 'ADAUSDT', coinName: 'Cardano' },
    { symbol: 'LINKUSDT', coinName: 'Chainlink' },
    { symbol: 'DOGEUSDT', coinName: 'Dogecoin' },
    { symbol: 'ETHUSDT', coinName: 'Ethereum' },
    { symbol: 'LTCUSDT', coinName: 'Litecoin' },
    { symbol: 'DOTUSDT', coinName: 'Polkadot' },
    { symbol: 'XRPUSDT', coinName: 'Ripple' },
    { symbol: 'SOLUSDT', coinName: 'Solana' },
    { symbol: 'TRXUSDT', coinName: 'TRON' }
  ]

  readonly cryptoOptions: CryptoOption[] = this.marketOverviewCryptos.map((asset) => ({
    symbol: asset.symbol,
    label: `${asset.coinName} (${asset.symbol})`
  }))

  selectedSymbol = 'BTCUSDT';
  investedValue: number | null = null;
  currentPrice: number | null = null;

  isSubmitting = false;
  isLoadingPrice = false;
  submitError = '';

  constructor(
    private portfolioService: PortfolioService,
    private marketDataService: MarketDataService
  ) {}

  ngOnChanges(changes: SimpleChanges): void {
    const openChange = changes['open']
    if (openChange?.currentValue === true) {
      this.loadCurrentPrice()
    }
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
    this.selectedSymbol = symbol
    this.loadCurrentPrice()
  }

  addToPortfolio(form: NgForm): void {
    if (form.invalid || this.isSubmitting) {
      return;
    }

    const normalizedInvestedValue = Number(this.investedValue);
    if (!Number.isFinite(normalizedInvestedValue) || normalizedInvestedValue <= 0) {
      this.submitError = 'Investment amount must be greater than zero.';
      return;
    }

    if (!this.currentPrice || !Number.isFinite(this.currentPrice) || this.currentPrice <= 0) {
      this.submitError = 'Could not load current market price. Please try again.';
      return;
    }

    const calculatedQuantity = normalizedInvestedValue / this.currentPrice;
    if (!Number.isFinite(calculatedQuantity) || calculatedQuantity <= 0) {
      this.submitError = 'Could not calculate quantity for this purchase.';
      return;
    }

    this.isSubmitting = true;
    this.submitError = '';

    this.portfolioService
      .createTransaction({
        fkUser: null,
        symbol: this.selectedSymbol,
        type: 'BUY',
        quantity: Number(calculatedQuantity.toFixed(8)),
        price: this.currentPrice
      })
      .subscribe({
        next: () => {
          this.isSubmitting = false;
          this.assetCreated.emit();
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

  get equivalentQuantity(): number {
    if (!this.currentPrice || !this.investedValue || this.currentPrice <= 0) {
      return 0
    }

    const quantity = Number(this.investedValue) / this.currentPrice
    return Number.isFinite(quantity) ? quantity : 0
  }

  get formattedEquivalentQuantity(): string {
    if (this.equivalentQuantity <= 0) {
      return '--'
    }

    return `${this.equivalentQuantity.toLocaleString('en-US', {
      minimumFractionDigits: 0,
      maximumFractionDigits: 8
    })} ${this.assetCode}`
  }

  get formattedCurrentPrice(): string {
    if (!this.currentPrice || this.currentPrice <= 0) {
      return '--'
    }

    return new Intl.NumberFormat('en-US', {
      style: 'currency',
      currency: 'USD',
      minimumFractionDigits: 2,
      maximumFractionDigits: 2
    }).format(this.currentPrice)
  }

  get assetCode(): string {
    return this.selectedSymbol.replace('USDT', '')
  }

  private loadCurrentPrice(): void {
    this.isLoadingPrice = true
    this.submitError = ''

    this.marketDataService.getPrice(this.selectedSymbol).subscribe({
      next: (priceResponse) => {
        this.currentPrice = this.parseNumeric(priceResponse.price)
        this.isLoadingPrice = false
      },
      error: () => {
        this.currentPrice = null
        this.isLoadingPrice = false
      }
    })
  }

  private parseNumeric(value: number | string): number {
    const parsed = typeof value === 'number' ? value : Number(value)
    return Number.isFinite(parsed) ? parsed : 0
  }

  private resolveErrorMessage(error: HttpErrorResponse): string {
    const rawError = error.error as { message?: string } | string | null;
    if (rawError && typeof rawError === 'object' && rawError.message) {
      return rawError.message;
    }

    if (typeof rawError === 'string' && rawError.trim()) {
      return rawError;
    }

    return 'Could not register transaction. Please try again.';
  }

  private resetTransientState(): void {
    this.isSubmitting = false;
    this.isLoadingPrice = false;
    this.submitError = '';
    this.selectedSymbol = 'BTCUSDT';
    this.investedValue = null;
    this.currentPrice = null;
  }
}
