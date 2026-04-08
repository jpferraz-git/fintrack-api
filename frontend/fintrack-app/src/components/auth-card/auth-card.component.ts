import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-auth-card',
  imports: [],
  templateUrl: './auth-card.component.html',
  styleUrl: './auth-card.component.css'
})
export class AuthCard {
  @Input({ required: true }) title = '';
}