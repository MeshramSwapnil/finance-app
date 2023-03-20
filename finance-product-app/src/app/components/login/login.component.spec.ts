import { HttpClient } from '@angular/common/http';
import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/services/auth/authentication.service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { LoginComponent } from './login.component';
import { AuthRequest } from 'src/app/models/model';
import { of } from 'rxjs';
import { RouterTestingModule } from '@angular/router/testing';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let httpMock: HttpTestingController;
  let mockAuthService:AuthenticationService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports : [
        FormsModule,
        ReactiveFormsModule,
        HttpClientTestingModule,
        RouterTestingModule
      ],
      declarations: [ LoginComponent ],
      providers : [AuthenticationService, HttpClient, Router,HttpTestingController]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    httpMock = TestBed.inject(HttpTestingController);
    mockAuthService = TestBed.inject(AuthenticationService);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should create login form', () => {
    const compiled = fixture.nativeElement as HTMLElement;
    expect(compiled.getElementsByTagName('input#username')).toBeTruthy();
    expect(compiled.getElementsByTagName('input#password')).toBeTruthy();
    expect(compiled.getElementsByTagName('button[type=submit]')).toBeTruthy();
  });

  it('should button to be clicked', fakeAsync(() => {
    spyOn(component, 'doLogin');
    const compiled = fixture.nativeElement as HTMLElement;
    (compiled.querySelector('#username') as HTMLInputElement).value = 'Swapnil';
    (compiled.querySelector('#password') as HTMLInputElement).value = 'Swapnil';
    let button = compiled.querySelector('button');
    button?.click();
    tick(100);
    expect(component.doLogin).toHaveBeenCalled();
  }));


  // it('login button to be clicked and executed', fakeAsync(() => {
  //   const user : AuthRequest = { username : "Swapnil", password : "Swapnil"};
  //   let data = new FormData();
  //   data.append('username', user.username!)
  //   data.append('password', user.password!)
  //   component.doLogin(user);
  // }));
});
