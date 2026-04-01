import { Component, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-alerts-create-panel',
  imports: [],
  templateUrl: './alerts-create-panel.html'
})
export class AlertsCreatePanel {
  @Output() openModal = new EventEmitter<void>();
}