import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-form-input',
  imports: [],
  templateUrl: './form-input.component.html',
  styleUrl: './form-input.component.css'
})
export class FormInput {
  @Input({ required: true }) label = '';
  @Input({ required: true }) id = '';
  @Input() type = 'text';
  @Input() name = '';
  @Input() placeholder = '';
  @Input() autocomplete = '';
  @Input() value = '';
  @Input() required = false;
  @Output() valueChange = new EventEmitter<string>();

  onInput(event: Event): void {
    const nextValue = (event.target as HTMLInputElement).value;
    this.valueChange.emit(nextValue);
  }
}