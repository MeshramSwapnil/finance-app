import { Component, EventEmitter, forwardRef, Input, Output } from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';

@Component({
  selector: 'fpa-dropdown',
  templateUrl: './dropdown.component.html',
  styleUrls: ['./dropdown.component.scss'],
  providers : [ {
    provide: NG_VALUE_ACCESSOR,
    useExisting: forwardRef(() => DropdownComponent),
    multi: true
  }]
})
export class DropdownComponent implements ControlValueAccessor {

  @Input('data')
  data!: any[];

  @Input('defaultTitle')
  defaultTitle!: string;

  @Input('key')
  key: string = 'key';

  @Input('value')
  value: string = 'value';

  @Output() itemSelected: EventEmitter<any> = new EventEmitter();

  @Input('selectedItem') selectedItem: any;

  @Input('selectedValue') selectedValue: any;

  showMenuItems = false;

  selected : any;
  disabled = false;
  private onTouched!: Function;
  private onChanged!: Function;

  selectItem(currentItem: any): void {
    this.showMenuItems = false;
    this.onTouched(); // <-- mark as touched
    this.data.filter(
      (item: any) => {
        if (currentItem != item) item.isSelected = false;
      }
    );
    if(!currentItem.isSelected)  currentItem.isSelected = true ;
    this.selectedItem = currentItem.isSelected ? currentItem : null;
    this.itemSelected.emit({ id: currentItem.isSelected ? currentItem.id : 0, [this.key]: currentItem[this.key] });
    this.selectedValue = currentItem[this.value];
    this.onChanged(this.selectedValue); // <-- call function to let know of a change
  }

  writeValue(obj: any): void {
    console.log(obj);

    this.selectedValue = obj[this.value] ?? '';
  }
  registerOnChange(fn: any): void {
    this.onChanged = fn; // <-- save the function

  }
  registerOnTouched(fn: any): void {
    this.onTouched = fn; // <-- save the function
  }
  setDisabledState(isDisabled: boolean) {
    this.disabled = isDisabled;
  }
}
