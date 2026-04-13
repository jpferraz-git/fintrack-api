import { Injectable } from '@angular/core';

interface FormatUsdOptions {
  compact?: boolean;
  minimumFractionDigits?: number;
  maximumFractionDigits?: number;
  fallback?: string;
}

interface FormatQuantityOptions {
  minimumFractionDigits?: number;
  maximumFractionDigits?: number;
  fallback?: string;
}

interface FormatPercentageOptions {
  decimals?: number;
  includeSign?: boolean;
  fallback?: string;
}

@Injectable({
  providedIn: 'root'
})
export class UtilsService {
  parseNumeric(value: number | string): number {
    const parsed = typeof value === 'number' ? value : Number(value);
    return Number.isFinite(parsed) ? parsed : 0;
  }

  formatUsd(value: number | string, options: FormatUsdOptions = {}): string {
    const {
      compact = false,
      minimumFractionDigits = 2,
      maximumFractionDigits = 2,
      fallback = '--'
    } = options;

    const parsedValue = Number(value);
    if (!Number.isFinite(parsedValue)) {
      return fallback;
    }

    return new Intl.NumberFormat('en-US', {
      style: 'currency',
      currency: 'USD',
      notation: compact ? 'compact' : 'standard',
      minimumFractionDigits,
      maximumFractionDigits
    }).format(parsedValue);
  }

  formatQuantity(value: number | string, options: FormatQuantityOptions = {}): string {
    const {
      minimumFractionDigits = 2,
      maximumFractionDigits = 8,
      fallback = '--'
    } = options;

    const parsedValue = Number(value);
    if (!Number.isFinite(parsedValue)) {
      return fallback;
    }

    return parsedValue.toLocaleString('en-US', {
      minimumFractionDigits,
      maximumFractionDigits
    });
  }

  formatPercentage(value: number, options: FormatPercentageOptions = {}): string {
    const { decimals = 2, includeSign = false, fallback = '--' } = options;
    if (!Number.isFinite(value)) {
      return fallback;
    }

    const sign = includeSign && value > 0 ? '+' : '';
    return `${sign}${value.toFixed(decimals)}%`;
  }

  toggleBoolean(value: boolean): boolean {
    return !value;
  }

  validatePassword(password: string): string {
    if (password.length < 8) {
      return 'Password must be at least 8 characters long.';
    }

    if (!/[A-Z]/.test(password)) {
      return 'Password must include at least one uppercase letter.';
    }

    if (!/[a-z]/.test(password)) {
      return 'Password must include at least one lowercase letter.';
    }

    if (!/\d/.test(password)) {
      return 'Password must include at least one number.';
    }

    if (!/[^A-Za-z0-9]/.test(password)) {
      return 'Password must include at least one special character.';
    }

    return '';
  }

  getStoredUser<T = Record<string, unknown>>(storage: 'local' | 'session' = 'local'): T | null {
    const storageRef = this.getStorage(storage);
    if (!storageRef) {
      return null;
    }

    const rawUser = storageRef.getItem('user');
    if (!rawUser) {
      return null;
    }

    try {
      return JSON.parse(rawUser) as T;
    } catch {
      return null;
    }
  }

  getStoredUserEmail(storage: 'local' | 'session' = 'local'): string | null {
    const user = this.getStoredUser<{ email?: string }>(storage);
    const email = user?.email?.trim();
    return email ? email : null;
  }

  private getStorage(storage: 'local' | 'session'): Storage | null {
    if (typeof window === 'undefined') {
      return null;
    }

    if (storage === 'local') {
      return typeof localStorage !== 'undefined' ? localStorage : null;
    }

    return typeof sessionStorage !== 'undefined' ? sessionStorage : null;
  }
}
