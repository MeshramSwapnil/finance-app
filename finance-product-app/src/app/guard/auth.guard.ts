import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { map, Observable, of } from 'rxjs';
import { AuthenticationService } from '../services/auth/authentication.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private auth : AuthenticationService, private _router : Router){}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
      if(this.auth.isAuthenticated){
        return of(true);
      } else {
        const token = this.auth.getRefreshToken();
        if(token){
          return this.auth.fetchRefreshToken().pipe(
            map((res) => res.status==200 && res.headers.get("Authorization") != null)
          );
        } else {
          this._router.navigateByUrl('/login')
          return of(false);
        }
      }
  }

}
