import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdditionalProductsComponent } from './additional-products.component';

describe('AdditionalProductsComponent', () => {
  let component: AdditionalProductsComponent;
  let fixture: ComponentFixture<AdditionalProductsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdditionalProductsComponent ]
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
