import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { of } from 'rxjs';
import { ProductsService } from 'src/app/services/products/products.service';

import { AdditionalProductsComponent } from './additional-products.component';

describe('AdditionalProductsComponent', () => {
  let component: AdditionalProductsComponent;
  let fixture: ComponentFixture<AdditionalProductsComponent>;
  let productService = jasmine.createSpyObj('ProductsService', {
    'getSelectedProducts' :  [],
    'getAllCheckBookData' : of([]),
    'getAllCurrencyData' : of([])
  })

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports : [HttpClientTestingModule, FormsModule, ReactiveFormsModule],
      declarations: [ AdditionalProductsComponent ],
      providers : [{
        provide : ProductsService,
        useValue : productService
      }]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdditionalProductsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
