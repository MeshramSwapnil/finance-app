import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AuthenticationInterceptor } from './interceptors/authentication.interceptor';
import { ProductsComponent } from './components/dashboard/products/products.component';
import { AdditionalProductsComponent } from './components/dashboard/additional-products/additional-products.component';
import { DropdownComponent } from './components/shared/dropdown/dropdown.component';
import { AddProductComponent } from './components/dashboard/add-product/add-product.component';
import { TermsAndConditionComponent } from './components/dashboard/terms-and-condition/terms-and-condition.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    DashboardComponent,
    ProductsComponent,
    AdditionalProductsComponent,
    DropdownComponent,
    AddProductComponent,
    TermsAndConditionComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [{
    provide: HTTP_INTERCEPTORS, useClass: AuthenticationInterceptor, multi:true,
  }],
  bootstrap: [AppComponent]
})
export class AppModule { }
