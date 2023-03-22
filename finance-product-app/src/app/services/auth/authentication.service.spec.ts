import { HttpClient, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { of } from 'rxjs';

import { AuthenticationService } from './authentication.service';

describe('AuthenticationService', () => {
  let service: AuthenticationService;
  let httpClient = jasmine.createSpyObj('HttpClient',{
    'post' : of({}),
    'get' : of(new HttpResponse<Object>())
  });
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ HttpClientTestingModule ],
      providers: [{
        provide: HttpClient, useValue: httpClient
      }]
    });
    service = TestBed.inject(AuthenticationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('check do login', () => {
    let formData = new FormData();
    service.doLogin(formData);
    expect(httpClient.post).toHaveBeenCalled()
  });

  it('check fetchRefreshToken', () => {
    service.setRefreshToken('Token');
    service.fetchRefreshToken();
    httpClient.get.and.returnValue(of(new HttpResponse<Object>()))
    expect(httpClient.get).toHaveBeenCalled()
  });

});
