import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-auth-card',
  imports: [],
  templateUrl: './auth-card.html'
})
export class AuthCard {
  @Input({ required: true }) title = '';
  @Input({ required: true }) subtitle = '';
}