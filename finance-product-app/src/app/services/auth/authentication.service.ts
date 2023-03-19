import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, tap } from 'rxjs';
import { environment } from 'src/environments/environment.development';
import jwt_decode from "jwt-decode";
@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private authorizationHeader : string = "Authorization";
  private xRefreshToken : string = "x-refresh-token";

  private authToken : string|null = null;
  private refreshToken : string|null = null;
  public isAuthenticated : boolean = false;
  public roles:string[] = [];
  public username:string = '';

  constructor(private http : HttpClient) { }


  doLogin(formData : FormData){
    return this.http.post(environment.apiUrl + '/login', formData, {observe: 'response'});
  }

  handleAuthentication(response : HttpResponse<Object>){
    let token = response.headers.get(this.authorizationHeader);
    if(token){
      this.authToken = token;
      let jwt = jwt_decode(this.authToken) as any;
      if(jwt){
        this.roles = jwt.role || [];
        this.username = jwt.sub || '';
      }
      this.isAuthenticated = true;
    }
    let refreshToken = response.headers.get(this.xRefreshToken);
    if(refreshToken){
      this.refreshToken = refreshToken;
      sessionStorage.setItem(this.xRefreshToken, this.refreshToken!);
    }
  }

  fetchRefreshToken(){
    let headers = new HttpHeaders();
    headers = headers.set(this.xRefreshToken, this.getRefreshToken()!);
    return this.http.get(environment.apiUrl + '/refreshToken', { headers : headers, observe: 'response'}).pipe(
      tap((response : HttpResponse<Object>) => this.handleAuthentication(response)),
    );
  }

  getToken() {
    return this.authToken;
  }

  setToken(token : string) {
    this.authToken = token;
  }

  getRefreshToken() {
    return this.refreshToken != null ? this.refreshToken : sessionStorage.getItem(this.xRefreshToken);
  }

  setRefreshToken(token : string) {
    this.authToken = token;
  }
}
