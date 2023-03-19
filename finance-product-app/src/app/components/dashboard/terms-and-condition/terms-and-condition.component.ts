import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Products } from 'src/app/models/model';
import { ProductsService } from 'src/app/services/products/products.service';

@Component({
  selector: 'fpa-terms-and-condition',
  templateUrl: './terms-and-condition.component.html',
  styleUrls: ['./terms-and-condition.component.scss']
})
export class TermsAndConditionComponent implements OnInit{

  constructor(private productService : ProductsService, private router : Router, private fb : FormBuilder){}
  products : Products[] = [];
  tncForm = this.fb.group({
    checkboxes: new FormArray([])
  })

  get checkboxes() : FormArray {
    return this.tncForm.get("checkboxes") as FormArray
  }

  ngOnInit(): void {
    if(this.productService.getSelectedProducts().length == 0) {
      alert('No Products Selected')
      this.router.navigateByUrl('/dashboard');
    }
    this.products = this.productService.getSelectedProducts();
    this.products.forEach(item => {
      this.checkboxes.push(new FormControl(false));
    });
  }

  downloadTnc(id : number){
    this.productService.downloadTnc(id).subscribe({
      next : (response) => {
        let file = new Blob([response], { type: 'application/pdf' });
        var fileURL = URL.createObjectURL(file);
        window.open(fileURL);
      }
    })
  }

  submitForm(){
    console.log(this.tncForm.value);

  }

}
