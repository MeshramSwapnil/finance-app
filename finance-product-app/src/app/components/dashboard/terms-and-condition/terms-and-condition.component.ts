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
    checkboxes: this.fb.array([])
  })

  get checkboxes() : FormArray {
    return this.tncForm.get("checkboxes") as FormArray
  }

  ngOnInit(): void {
    if(this.productService.getSelectedProducts().length == 0) {
      alert('No Products Selected')
      this.router.navigateByUrl('/dashboard');
      return;
    }
    this.products = this.productService.getSelectedProducts();
    for(let i=0; i < this.products.length; i++){
      this.checkboxes.push(new FormControl(false, Validators.requiredTrue));
    }
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
    alert('Form Submitted Successfully')
    this.router.navigateByUrl('/dashboard');
  }

}
