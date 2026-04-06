import { NgIf } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { PortfolioService } from '../../../app/services/portfolio.service';

@Component({
  selector: 'app-add-asset-modal',
  imports: [NgIf, FormsModule],
  templateUrl: './add-asset-modal.component.html',
  styleUrl: './add-asset-modal.component.css'
})
export class AddAssetModal {
  @Input() open = false;
  @Output() closed = new EventEmitter<void>();
  @Output() assetCreated = new EventEmitter<void>();

  symbolInput = '';
  quantity: number | null = null;
  avgPrice: number | null = null;

  isSubmitting = false;
  submitError = '';

  constructor(private portfolioService: PortfolioService) {}

  close(): void {
    this.resetTransientState();
    this.closed.emit();
  }

  onBackdropClick(event: MouseEvent): void {
    if (event.target === event.currentTarget) {
      this.close();
    }
  }

  addToPortfolio(form: NgForm): void {
    if (form.invalid || this.isSubmitting) {
      return;
    }

    const normalizedSymbol = this.normalizeSymbol(this.symbolInput);
    if (!normalizedSymbol) {
      this.submitError = 'Please provide a valid symbol.';
      return;
    }

    const normalizedQuantity = Number(this.quantity);
    const normalizedAvgPrice = Number(this.avgPrice);

    if (!Number.isFinite(normalizedQuantity) || normalizedQuantity <= 0) {
      this.submitError = 'Amount held must be greater than zero.';
      return;
    }

    if (!Number.isFinite(normalizedAvgPrice) || normalizedAvgPrice <= 0) {
      this.submitError = 'Average buy price must be greater than zero.';
      return;
    }

    this.isSubmitting = true;
    this.submitError = '';

    this.portfolioService
      .createAsset({
        fkUser: null,
        symbol: normalizedSymbol,
        type: 'CRYPTO',
        quantity: normalizedQuantity,
        avgPrice: normalizedAvgPrice
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

  get estimatedValue(): number {
    const currentQuantity = this.quantity ?? 0;
    const currentAvgPrice = this.avgPrice ?? 0;
    return currentQuantity * currentAvgPrice;
  }

  get formattedEstimatedValue(): string {
    return new Intl.NumberFormat('en-US', {
      style: 'currency',
      currency: 'USD',
      minimumFractionDigits: 2,
      maximumFractionDigits: 2
    }).format(this.estimatedValue);
  }

  private normalizeSymbol(rawSymbol: string): string {
    const cleanedSymbol = rawSymbol.trim().toUpperCase().replace(/[^A-Z0-9]/g, '');
    if (!cleanedSymbol) {
      return '';
    }

    return cleanedSymbol.endsWith('USDT') ? cleanedSymbol : `${cleanedSymbol}USDT`;
  }

  private resolveErrorMessage(error: HttpErrorResponse): string {
    const rawError = error.error as { message?: string } | string | null;
    if (rawError && typeof rawError === 'object' && rawError.message) {
      return rawError.message;
    }

    if (typeof rawError === 'string' && rawError.trim()) {
      return rawError;
    }

    return 'Could not create asset. Please try again.';
  }

  private resetTransientState(): void {
    this.isSubmitting = false;
    this.submitError = '';
    this.symbolInput = '';
    this.quantity = null;
    this.avgPrice = null;
  }
}
