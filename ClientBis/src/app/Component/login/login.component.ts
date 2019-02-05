import { Component, OnInit } from '@angular/core';
import { AuthenticateService } from 'src/app/service/LoginService/authenticate.service';
import { User } from 'src/app/model/user';
import { Router } from '@angular/router';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  credentials: User;
  ngOnInit() {
    this.credentials = new User();
  }

  constructor(private loginService: AuthenticateService, private router: Router) { }

  login() {
    this.loginService.authenticate(this.credentials, (() => { this.router.navigateByUrl('/'); })).subscribe(response => {
      console.log("success");
      if (response['access_token']) {
        console.log("is authenticated");
        localStorage.setItem("access_token", response['access_token'])
        if (response) {
          console.log("response");
          console.log(response);
          this.loginService.setLoggedUser(this.credentials);
        }
      } else {
        console.log("not authenticated");
        this.router.navigateByUrl('/login');
      }
      this.router.navigateByUrl('/');
    },
      error => {
        this.router.navigateByUrl('/login');
      });
  }
}
