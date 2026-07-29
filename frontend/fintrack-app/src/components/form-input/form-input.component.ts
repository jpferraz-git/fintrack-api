import { Component, EventEmitter, forwardRef, Input, Output } from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';

@Component({
  selector: 'app-form-input',
  imports: [],
  templateUrl: './form-input.component.html',
  styleUrl: './form-input.component.css',
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => FormInput),
      multi: true
    }
  ]
})
export class FormInput implements ControlValueAccessor {
  @Input({ required: true }) label = '';
  @Input({ required: true }) id = '';
  @Input() type = 'text';
  @Input() name = '';
  @Input() placeholder = '';
  @Input() autocomplete = '';
  @Input() required = false;
  
  value = '';
  @Output() valueChange = new EventEmitter<string>();

  onChange: any = () => {};
  onTouched: any = () => {};

  writeValue(val: any): void {
    this.value = val;
  }

  registerOnChange(fn: any): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouched = fn;
  }

  onInput(event: Event): void {
    const nextValue = (event.target as HTMLInputElement).value;
    this.value = nextValue;
    this.valueChange.emit(nextValue);
    this.onChange(nextValue);
    this.onTouched();
  }
}