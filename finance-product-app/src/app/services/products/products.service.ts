import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ApiResponse, Products } from 'src/app/models/model';
import { environment } from 'src/environments/environment.development';

@Injectable({
  providedIn: 'root'
})
export class ProductsService {

  selectedProducts  : Products[] = [];

  constructor(private http : HttpClient) { }

  getAllProducts(){
    return this.http.get<ApiResponse>(environment.apiUrl+'/products');
  }

  deleteProduct(id:number){
    return this.http.delete<ApiResponse>(environment.apiUrl+'/products',{body : id})
  }

  addProduct(val:FormData){
    return this.http.post<ApiResponse>(environment.apiUrl+'/products',val)
  }

  getAllCheckBookData(){
    return this.http.get<ApiResponse>(environment.apiUrl+'/checkbook');
  }

  getAllCurrencyData(){
    return this.http.get<ApiResponse>(environment.apiUrl+'/currency');
  }

  setSelectedProducts(products : Products[]){
    this.selectedProducts = products;
    sessionStorage.setItem('products', JSON.stringify(this.selectedProducts))
  }

  getSelectedProducts() {
    return this.selectedProducts.length > 0 ? this.selectedProducts : JSON.parse(sessionStorage.getItem('products') ?? '');
  }

  downloadTnc(id:number) {
    return this.http.get(environment.apiUrl+`/termsandcondition/${id}`,  { responseType: 'blob' });
  }
}
