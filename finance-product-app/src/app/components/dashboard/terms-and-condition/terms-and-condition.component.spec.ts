import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ProductsService } from 'src/app/services/products/products.service';

import { TermsAndConditionComponent } from './terms-and-condition.component';

describe('TermsAndConditionComponent', () => {
  let component: TermsAndConditionComponent;
  let fixture: ComponentFixture<TermsAndConditionComponent>;

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
      }]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TermsAndConditionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
