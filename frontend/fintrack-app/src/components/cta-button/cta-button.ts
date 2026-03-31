import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-cta-button',
  imports: [],
  templateUrl: './cta-button.html'
})
export class CtaButton {
  @Input() type: 'button' | 'submit' | 'reset' = 'button';
}