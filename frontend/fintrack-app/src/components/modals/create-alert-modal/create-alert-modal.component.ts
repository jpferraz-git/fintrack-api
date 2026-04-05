import { NgIf } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-create-alert-modal',
  imports: [NgIf],
  templateUrl: './create-alert-modal.component.html',
  styleUrl: './create-alert-modal.component.css'
})
export class CreateAlertModal {
  @Input() open = false;
  @Output() closed = new EventEmitter<void>();

  close(): void {
    this.closed.emit();
  }

  onBackdropClick(event: MouseEvent): void {
    if (event.target === event.currentTarget) {
      this.close();
    }
  }
}
