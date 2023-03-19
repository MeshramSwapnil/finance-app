import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AddProductComponent } from './components/dashboard/add-product/add-product.component';
import { AdditionalProductsComponent } from './components/dashboard/additional-products/additional-products.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { ProductsComponent } from './components/dashboard/products/products.component';
import { TermsAndConditionComponent } from './components/dashboard/terms-and-condition/terms-and-condition.component';
import { LoginComponent } from './components/login/login.component';
import { AuthGuard } from './guard/auth.guard';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  {
    path: 'dashboard',
    component: DashboardComponent,
    children : [
      {path : '', component : ProductsComponent},
      {path : 'addProduct', component : AddProductComponent},
      {path : 'termsAndCondition', component : TermsAndConditionComponent},
      {path : 'additinalproducts', component : AdditionalProductsComponent}
    ],
    canActivate: [AuthGuard],
  },
  { path: '**', redirectTo: 'login' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
