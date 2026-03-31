import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-form-input',
  imports: [],
  templateUrl: './form-input.html'
})
export class FormInput {
  @Input({ required: true }) label = '';
  @Input({ required: true }) id = '';
  @Input() type = 'text';
  @Input() name = '';
  @Input() placeholder = '';
  @Input() autocomplete = '';
}