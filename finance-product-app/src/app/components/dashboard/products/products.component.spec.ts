import { HttpClient, HttpClientModule } from '@angular/common/http';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ComponentFixture, fakeAsync, TestBed } from '@angular/core/testing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';
import { AuthenticationService } from 'src/app/services/auth/authentication.service';
import { ProductsService } from 'src/app/services/products/products.service';

import { ProductsComponent } from './products.component';

describe('ProductsComponent', () => {
  // let component: ProductsComponent;
  // let fixture: ComponentFixture<ProductsComponent>;
  // let mockHttpClient: HttpTestingController;
  // let mockAuthService:AuthenticationService;
  // let mockProductService : ProductsService;


  // const productsService = jasmine.createSpyObj('ProductsService', {
  //   getAllProducts :  of({})
  // });


  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports : [
        HttpClientTestingModule,
        RouterTestingModule
      ],
      declarations: [ ProductsComponent ],
      providers : []
    })
    .compileComponents();

    // fixture = TestBed.createComponent(ProductsComponent);
    // component = fixture.componentInstance;
    // fixture.detectChanges();
  });

  it('should create', () => {
    const fixture = TestBed.createComponent(ProductsComponent);
    const component = fixture.debugElement.componentInstance;
    expect(component).toBeTruthy();
  });
});
