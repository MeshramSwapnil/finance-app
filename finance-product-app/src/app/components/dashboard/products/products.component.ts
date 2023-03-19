import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { map, Observable, Subscription } from 'rxjs';
import { ApiResponse, Products } from 'src/app/models/model';
import { AuthenticationService } from 'src/app/services/auth/authentication.service';
import { ProductsService } from 'src/app/services/products/products.service';

@Component({
  selector: 'fpa-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.scss']
})
export class ProductsComponent implements OnInit{

  constructor(private productService : ProductsService, private router : Router, public auth : AuthenticationService){}
  products$ : Observable<Products[]> | undefined;
  selectedProducts : Products[] = [];

  ngOnInit(): void {
    this.products$ = this.productService.getAllProducts().pipe(map(res => res.data as Products[]));
  }


  selectCard(product : Products){
    product.selected = !product.selected;
    if(product.selected){
      this.selectedProducts.push(product);
    } else {
      this.selectedProducts = this.selectedProducts.filter(p => product.id !== p.id);
    }
    this.productService.setSelectedProducts(this.selectedProducts)
  }

  deleteProduct(id : number){
    this.productService.deleteProduct(id).subscribe({
      next : (res) => {
        console.log(res),
        alert('Product Deleted Successfully');
        this.ngOnInit();
      },
      error : (err) => alert(err)
    })
  }

  proceedToBuy(){
    if(this.selectedProducts.length == 0) return;
    this.router.navigateByUrl('/dashboard/additinalproducts');
  }

  goToAddProduct(){
    this.router.navigateByUrl('/dashboard/addProduct');
  }
}
