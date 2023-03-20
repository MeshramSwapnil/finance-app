import { HttpClientTestingModule } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { ActivatedRouteSnapshot, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable, of } from 'rxjs';
import { AuthenticationService } from '../services/auth/authentication.service';

import { AuthGuard } from './auth.guard';

describe('AuthGuard', () => {
  let guard: AuthGuard;
  let authService : AuthenticationService;
  let router = jasmine.createSpyObj('Router',['navigateByUrl'])


  beforeEach(() => {
    TestBed.configureTestingModule({
      imports : [HttpClientTestingModule],
      providers : [AuthenticationService,{provide: Router, useValue: router}]
    });
    guard = TestBed.inject(AuthGuard);
    authService = TestBed.inject(AuthenticationService);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });

  it('user is autheticated and allowed to route', () => {
    authService.isAuthenticated = true;
    expect(guard.canActivate(new ActivatedRouteSnapshot(), <RouterStateSnapshot>{url: 'testUrl'})).toBeDefined();
    expect(guard.canActivate(new ActivatedRouteSnapshot(), <RouterStateSnapshot>{url: 'testUrl'})).toBeTruthy()
    // @ts-ignore
    guard.canActivate(new ActivatedRouteSnapshot(), <RouterStateSnapshot>{url: 'testUrl'}).subscribe((res) => {
      expect(res).toBe(true);
    });
  });

  it('user is unautheticated and not allowed to route', () => {
    authService.isAuthenticated = false;
    expect(guard.canActivate(new ActivatedRouteSnapshot(), <RouterStateSnapshot>{url: 'testUrl'})).toBeInstanceOf(Observable);
    expect(router.navigateByUrl).toHaveBeenCalled();
    // @ts-ignore
    guard.canActivate(new ActivatedRouteSnapshot(), <RouterStateSnapshot>{url: 'testUrl'}).subscribe((res) => {
      expect(res).toBe(false);
    });
  });
});
