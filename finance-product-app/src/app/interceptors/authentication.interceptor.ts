import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpErrorResponse
} from '@angular/common/http';
import { BehaviorSubject, catchError, Observable, switchMap, throwError } from 'rxjs';
import { AuthenticationService } from '../services/auth/authentication.service';

@Injectable()
export class AuthenticationInterceptor implements HttpInterceptor {

  private isRefreshing = false;

  constructor(private authService : AuthenticationService) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {

    const token = this.authService.getToken();
    if(token){
      request = request.clone({ setHeaders: { Authorization: `Bearer ${token}`}})
    }
    return next.handle(request).pipe(
      catchError((err) => {
        if (err instanceof HttpErrorResponse) {
            if (err.status === 401 && err?.error?.detail == "Token Expired" && this.authService.getRefreshToken() != null) {
              if (!this.isRefreshing) {
                this.isRefreshing = true;
                const token = this.authService.getRefreshToken();
                if (token) {
                  return  this.authService.fetchRefreshToken().pipe(
                    switchMap((res: any) => {
                      const authToken = this.authService.getToken();
                      this.isRefreshing = false;
                      return next.handle(request.clone({ setHeaders: { Authorization: `Bearer ${authToken}`}}));
                    }),
                    catchError((err) => {
                      this.isRefreshing = false;
                      return throwError(() => err);
                    })
                  );
                }
              }
            } else {
              return throwError(() => err);
            }
      }
      return throwError(() => err);
    }));
  }
}
