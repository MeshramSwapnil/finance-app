import { Component } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { ApiResponse, AuthRequest, AuthResponse } from 'src/app/models/model';
import { AuthenticationService } from 'src/app/services/auth/authentication.service';

@Component({
  selector: 'fpa-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {

  constructor(private router : Router, private auth : AuthenticationService){}
  errorMessage : string = "";


  user : AuthRequest = { username : undefined, password : undefined};

  doLogin(data : AuthRequest){
    let formData = new FormData();
    formData.append('username', data.username!);
    formData.append('password', data.password!);
    this.auth.doLogin(formData).subscribe({
      next : (response) => {
        if(response.status == 200) {
          this.auth.handleAuthentication(response);
          let res = response.body as AuthResponse;
          console.log(res);
          if(res.success){
            this.router.navigateByUrl("/dashboard")
          }
        }
        else this.errorMessage = "Authentication Failed";
      },
      error : (err) => {
        console.error(err);
        this.errorMessage = err.error?.message || "Authentication Failed";
      }
    })
  }

}
