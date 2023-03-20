import { HttpClientTestingModule } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { AuthenticationService } from '../services/auth/authentication.service';

import { AuthenticationInterceptor } from './authentication.interceptor';

describe('AuthenticationInterceptor', () => {

  let authService = jasmine.createSpyObj('AuthenticationService',{
    'getToken' : 'token'
  })
  let request = jasmine.createSpyObj('HttpRequest',['clone'])
  let next = jasmine.createSpyObj('HttpHandler',['handle'])

  let interceptor : AuthenticationInterceptor;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports : [
        HttpClientTestingModule
      ],
      providers: [
        AuthenticationInterceptor,
        {
          provide : AuthenticationService,
          useValue : authService
        }
        ]
    })
     interceptor = TestBed.inject(AuthenticationInterceptor);
  });

  it('should be created', () => {
    expect(interceptor).toBeTruthy();
  });

  it('should be intercept', () => {
    next.handle.and.returnValue(of({}));
    expect(interceptor.intercept(request,next)).toBeTruthy();
    expect(request.clone).toHaveBeenCalled();
    expect(next.handle).toHaveBeenCalled();
  });

});
