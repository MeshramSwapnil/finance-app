import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { map } from 'rxjs';
import { ApiResponse, CheckBook, Currency, Products } from 'src/app/models/model';
import { ProductsService } from 'src/app/services/products/products.service';

@Component({
  selector: 'fpa-add-product',
  templateUrl: './add-product.component.html',
  styleUrls: ['./add-product.component.scss']
})
export class AddProductComponent implements OnInit{

  constructor(private productService : ProductsService, private router : Router) {}

  productForm : FormGroup = new FormGroup({});
  imageFile : {link?: string, file?: any, name?: string} = {};
  file : File | undefined;


  ngOnInit(): void {
    this.productForm = new FormGroup({
      name: new FormControl('', [Validators.minLength(2), Validators.required]),
      description: new FormControl('', [Validators.minLength(2), Validators.required]),
      charge: new FormControl('', [Validators.required]),
    })

  }


  imagesPreview(event : Event | any) {
    if (event.target.files && event.target.files[0]) {
      this.file = event.target.files[0];

        const reader = new FileReader();
        reader.onload = (_event: any) => {
            this.imageFile = {
                link: _event.target.result,
                file: event.srcElement.files[0],
                name: event.srcElement.files[0].name
            };

        };
        reader.readAsDataURL(event.target.files[0]);
    }
  }

  addProduct(){
    let data = new FormData();
    data.append('data', new Blob([JSON.stringify(this.productForm.value)], {'type' : 'application/json'}));
    data.append('file', this.file!);
    this.productService.addProduct(data).subscribe({
      next : (res : ApiResponse) => {
        if(res.message == 'SUCCESS') {
          alert('Product Added Successfully')
          this.router.navigateByUrl('/dashboard')
        }
      },
      error : (err) => alert(err.error.detail)
    })
  }
}
