import { Component, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-portfolio-action-card',
  imports: [],
  templateUrl: './portfolio-action-card.html',
  styleUrl: './portfolio-action-card.css'
})
export class PortfolioActionCard {
  @Output() openModal = new EventEmitter<void>();
}
