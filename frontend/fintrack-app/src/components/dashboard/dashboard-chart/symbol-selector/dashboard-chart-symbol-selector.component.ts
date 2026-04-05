import { NgFor } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';

export type ChartSymbolOption = {
  symbol: string;
  label: string;
};

@Component({
  selector: 'app-dashboard-chart-symbol-selector',
  imports: [NgFor],
  templateUrl: './dashboard-chart-symbol-selector.component.html',
  styleUrl: './dashboard-chart-symbol-selector.component.css'
})
export class DashboardChartSymbolSelectorComponent {
  @Input() options: ChartSymbolOption[] = [];
  @Input() activeSymbol = 'BTCUSDT';

  @Output() readonly symbolSelected = new EventEmitter<string>();

  trackBySymbol(_index: number, option: ChartSymbolOption): string {
    return option.symbol;
  }

  onSelectionChange(event: Event): void {
    const target = event.target as HTMLSelectElement | null;
    if (!target?.value) {
      return;
    }

    this.symbolSelected.emit(target.value);
  }
}