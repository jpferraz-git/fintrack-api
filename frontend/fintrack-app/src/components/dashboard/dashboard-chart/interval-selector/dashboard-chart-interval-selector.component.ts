import { NgFor } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';

export type ChartIntervalOption = {
  value: string;
  label: string;
};

@Component({
  selector: 'app-dashboard-chart-interval-selector',
  imports: [NgFor],
  templateUrl: './dashboard-chart-interval-selector.component.html',
  styleUrl: './dashboard-chart-interval-selector.component.css'
})
export class DashboardChartIntervalSelectorComponent {
  @Input() intervals: ChartIntervalOption[] = [];
  @Input() activeInterval = '15m';

  @Output() readonly intervalSelected = new EventEmitter<string>();

  trackByValue(_index: number, option: ChartIntervalOption): string {
    return option.value;
  }

  selectInterval(value: string): void {
    this.intervalSelected.emit(value);
  }
}