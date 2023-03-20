import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { FormArray, FormBuilder, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { Products } from 'src/app/models/model';
import { ProductsService } from 'src/app/services/products/products.service';

import { TermsAndConditionComponent } from './terms-and-condition.component';

describe('TermsAndConditionComponent', () => {
  let component: TermsAndConditionComponent;
  let fixture: ComponentFixture<TermsAndConditionComponent>;
  let router = jasmine.createSpyObj('Router',['navigateByUrl'])
  let mockProduct:Products[]= [{
    charge : 100,
    description : "Card",
    id : 100,
    name : "Card"
  }]
  let productService = jasmine.createSpyObj('ProductsService', {
    'getSelectedProducts' : []
  })

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports : [HttpClientTestingModule, FormsModule, ReactiveFormsModule],
      declarations: [ TermsAndConditionComponent ],
      providers : [{
        provide : ProductsService,
        useValue : productService
      }, {
        provide : Router,
        useValue : router
      }, FormBuilder]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TermsAndConditionComponent);
    component = fixture.componentInstance;
    let fb = new FormBuilder();
    component.tncForm = fb.group({
      checkboxes: fb.array([])
    })
    productService.getSelectedProducts.and.returnValue([])
    fixture.detectChanges();
  });

  it('should create', () => {
    productService.getSelectedProducts.and.returnValue([])
    expect(component).toBeTruthy();

  });

  it('onInit check with products', () => {
    productService.getSelectedProducts.and.returnValue(mockProduct)
    component.ngOnInit();
    expect(productService.getSelectedProducts).toHaveBeenCalled();
    expect(component.products.length).toBe(1);
    expect(component.checkboxes.length).toBe(1);
  });

  it('onInit check with no products', () => {
    productService.getSelectedProducts.and.returnValue([])
    component.ngOnInit();
    expect(router.navigateByUrl).toHaveBeenCalled();
  });


  it('check checkboxes are rendered', () => {
    productService.getSelectedProducts.and.returnValue(mockProduct)
    component.ngOnInit();
    const compiled = fixture.nativeElement as HTMLElement;
    expect(compiled.querySelectorAll('input[type=checkbox]')).toBeTruthy();
  });

  it('check download button click', (() => {
    spyOn(component, 'downloadTnc');
    productService.getSelectedProducts.and.returnValue(mockProduct)
    component.ngOnInit();
    fixture.detectChanges();
    const compiled = fixture.nativeElement as HTMLElement;
    let button = compiled.querySelectorAll('a') as NodeListOf<HTMLAnchorElement>;
    expect(button).toBeTruthy();
    button.forEach(btn => btn.click());
    expect(component.downloadTnc).toHaveBeenCalled();
  }));


  it('checkboxes invalid', (() => {
    productService.getSelectedProducts.and.returnValue(mockProduct)
    component.ngOnInit();
    expect(component.tncForm.invalid).toBe(true);
  }));

  it('select checkboxes and valid', fakeAsync(() => {
    productService.getSelectedProducts.and.returnValue(mockProduct)
    component.ngOnInit();
    tick(2000)
    fixture.detectChanges();
    const compiled = fixture.nativeElement as HTMLElement ;
    let cb = compiled.querySelectorAll('input') as NodeListOf<HTMLInputElement>;
    cb.forEach(cb => cb.click())
    console.log(component.tncForm.value, cb)
    expect(component.tncForm.valid).toBe(true);
  }));
});
