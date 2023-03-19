import { HttpClient } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { interval, Subscription, switchMap } from 'rxjs';
import { ApiResponse } from 'src/app/models/model';
import { AuthenticationService } from 'src/app/services/auth/authentication.service';
import { ProductsService } from 'src/app/services/products/products.service';
import { environment } from 'src/environments/environment.development';

@Component({
  selector: 'fpa-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit, OnDestroy{

  constructor(private http : HttpClient, private router : Router){}
  ngOnInit(): void {

  }
  ngOnDestroy(): void {
  }

  logout() {
    this.http.get<ApiResponse>(environment.apiUrl+'/logout').subscribe({
      next : (res) => {
        if(res.message == "LogoutSucess"){
            sessionStorage.clear();
            this.router.navigateByUrl('/login');
        }
      },
      error : (err) => alert(err.error.message || 'Failed')
    })
  }

}
