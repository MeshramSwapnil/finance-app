import { HttpClient } from '@angular/common/http';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';

import { DashboardComponent } from './dashboard.component';

describe('DashboardComponent', () => {
  let component: DashboardComponent;
  let fixture: ComponentFixture<DashboardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports : [
        HttpClientTestingModule,
        RouterTestingModule
      ],
      declarations: [ DashboardComponent ],
      providers : [HttpClient, HttpTestingController, Router]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should header', () => {
    const html = fixture.nativeElement as HTMLElement;
    expect(html.querySelector('header')).toBeTruthy();
  });

  it('should render router-outlet', () => {
    const compiled = fixture.nativeElement as HTMLElement;
    expect(compiled.getElementsByTagName('router-outlet')).toBeTruthy();
  });

  it('should render header images', () => {
    const compiled = fixture.nativeElement as HTMLElement;
    expect(compiled.getElementsByClassName('logo')).toBeTruthy();
    expect(compiled.getElementsByClassName('logout')).toBeTruthy();
  });
});
