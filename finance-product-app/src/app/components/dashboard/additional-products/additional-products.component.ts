import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormControlStatus, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { map } from 'rxjs';
import { CheckBook, Currency, Products, Summary } from 'src/app/models/model';
import { ProductsService } from 'src/app/services/products/products.service';

@Component({
  selector: 'fpa-additional-products',
  templateUrl: './additional-products.component.html',
  styleUrls: ['./additional-products.component.scss']
})
export class AdditionalProductsComponent implements OnInit{


  checkBookList : CheckBook[] = [];
  currencyList : Currency[] = [];
  summary : Summary[] = [];
  psummary : Summary[] = [];
  total : number = 0;
  constructor(private fb:FormBuilder, private productService : ProductsService, private router : Router) {}

  productsForm : FormGroup = this.fb.group({
    additionalProducts: this.fb.array([]) ,
  });

  addEmptyRow(){
    const row = this.fb.group({
      rateId: ['', Validators.required],
      currency: ['',  Validators.required],
      quantity: ['',  Validators.required],
    })
    this.additionalProducts.push(row);
  }

  get additionalProducts() : FormArray {
    return this.productsForm.get("additionalProducts") as FormArray
  }

  ngOnInit(): void {
    if(this.productService.getSelectedProducts().length == 0) {
      this.router.navigateByUrl('/dashboard');
    }
    this.addProductsToSummary();
    this.productService.getAllCheckBookData().pipe(map(res => res.data as CheckBook[])).subscribe({
      next : (res) => {
        this.checkBookList = res;
        this.checkBookList.forEach(val => {
          val.description = `${val.pages} page checkbook (${val.ratePerPage}/unit)`;
        })

      },
      error : (err) => console.error(err)

    })
    this.productService.getAllCurrencyData().pipe(map(res => res.data as Currency[])).subscribe({
      next : (res) => {this.currencyList = res},
      error : (err) => console.error(err)
    });

    this.productsForm.statusChanges.subscribe((status : FormControlStatus) => {
        if(status == 'VALID'){
          this.updateSummary();
        } else {
          this.total = 0
        }
    });
  }

  addProductsToSummary(){
    let products : Products[] = this.productService.getSelectedProducts();
    for(let product of products){
      this.psummary.push({
        ratePerPage : 1,
        description : product.description,
        pages : 1,
        total : product.charge,
        quantity : 1
      })
    }
    for(let s of this.psummary) this.total+=s.total;
  }

  updateSummary(){
    let items = this.productsForm.value.additionalProducts;
    let calculatedSummary : Summary[] = [];
    for(let item of items){
      calculatedSummary.push({
        ratePerPage : this.checkBookList.find(val => val.id == item.rateId)?.ratePerPage!,
        description : this.checkBookList.find(val => val.id == item.rateId)?.description!,
        pages : this.checkBookList.find(val => val.id == item.rateId)?.pages!,
        total : this.checkBookList.find(val => val.id == item.rateId)?.ratePerPage! * item.quantity,
        quantity : item.quantity,
      })
    }
    this.summary = calculatedSummary;
    for(let s of this.summary) this.total+=s.total;
    for(let s of this.psummary) this.total+=s.total;

  };

  proceedToCheckout(){
    let products: Products[] = this.productService.getSelectedProducts();
    products.push({
      charge : 0,
      description : 'Check Book',
      id : 0,
      name : 'Check Book'
    })
    this.productService.setSelectedProducts(products);
    this.router.navigateByUrl('/dashboard/termsAndCondition')
  }

  removeRow(index:number){
    this.additionalProducts.removeAt(index);
  }

  skipToTermsAndCondition(){
    this.router.navigateByUrl('/dashboard/termsAndCondition')
  }

}
