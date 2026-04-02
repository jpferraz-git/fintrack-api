import { Component, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-alerts-create-panel',
  imports: [],
  templateUrl: './alerts-create-panel.component.html',
  styleUrl: './alerts-create-panel.component.css'
})
export class AlertsCreatePanel {
  @Output() openModal = new EventEmitter<void>();
}