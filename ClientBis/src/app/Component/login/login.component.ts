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

      credentials: User = new User();
      constructor(private loginService: AuthenticateService, private router: Router) { }

      ngOnInit() {
      }

      login() {
            console.log("hello");
            this.loginService.authenticate(this.credentials, (() => { this.router.navigateByUrl('/'); }));
      }

}
