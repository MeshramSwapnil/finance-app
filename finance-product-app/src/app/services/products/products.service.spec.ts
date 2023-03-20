import { HttpClient } from '@angular/common/http';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { ApiResponse } from 'src/app/models/model';

import { ProductsService } from './products.service';

describe('ProductsService', () => {
  let service: ProductsService;
  let httpClient: HttpClient;
  let httpTestingController: HttpTestingController;


  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ HttpClientTestingModule ]
    });
    httpClient = TestBed.inject(HttpClient);
    httpTestingController = TestBed.inject(HttpTestingController);
    service = TestBed.inject(ProductsService)
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });


  it('test getAllProducts', () => {
    const testData: ApiResponse = {data : {}, message : "", errorcode : 50000};
    service.getAllProducts().subscribe(data => {
      expect(data).toEqual(testData);
    })
    const req = httpTestingController.expectOne('http://localhost:8080/products');
    req.flush(testData);
  });

  it('test deleteProduct', () => {
    const testData: ApiResponse = {data : {}, message : "", errorcode : 50000};
    service.deleteProduct(100).subscribe(data => {
      expect(data).toEqual(testData);
    })
    const req = httpTestingController.expectOne('http://localhost:8080/products');
    req.flush(testData);
  });

  it('test addProduct', () => {
    let fdata = new FormData();
    const testData: ApiResponse = {data : {}, message : "", errorcode : 50000};
    service.addProduct(fdata).subscribe(data => {
      expect(data).toEqual(testData);
    })
    const req = httpTestingController.expectOne('http://localhost:8080/products');
    req.flush(testData);
  });

  it('test getCheckbookData', () => {
    const testData: ApiResponse = {data : {}, message : "", errorcode : 50000};
    service.getAllCheckBookData().subscribe(data => {
      expect(data).toEqual(testData);
    })
    const req = httpTestingController.expectOne('http://localhost:8080/checkbook');
    req.flush(testData);
  });

  it('test getCurrencyData', () => {
    const testData: ApiResponse = {data : {}, message : "", errorcode : 50000};
    service.getAllCurrencyData().subscribe(data => {
      expect(data).toEqual(testData);
    })
    const req = httpTestingController.expectOne('http://localhost:8080/currency');
    req.flush(testData);
  });
});
