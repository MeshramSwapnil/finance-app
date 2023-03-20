import { HttpClientTestingModule } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { AuthenticationService } from '../services/auth/authentication.service';

import { AuthenticationInterceptor } from './authentication.interceptor';

describe('AuthenticationInterceptor', () => {

  beforeEach(() => TestBed.configureTestingModule({
    imports : [
      HttpClientTestingModule
    ],
    providers: [
      AuthenticationInterceptor,
      AuthenticationService
      ]
  }));

  it('should be created', () => {
    const interceptor: AuthenticationInterceptor = TestBed.inject(AuthenticationInterceptor);
    expect(interceptor).toBeTruthy();
  });
});
