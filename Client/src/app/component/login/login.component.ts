import { Component, OnInit } from '@angular/core';
import { LogService } from "../../Service/User/log.service";
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { PetitUser } from 'src/app/Interface/petit-user';
import { LoggedUser } from 'src/app/model/logged-user';

@Component({
      selector: 'app-login',
      templateUrl: './login.component.html',
      styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

      credentials: LoggedUser = new LoggedUser();
      constructor(private logService: LogService, private router: Router, private http: HttpClient) { }

      ngOnInit() {
            this.credentials.email = "email or logi";
            this.credentials.password = "password sdsdsdds";
      }

      login() {
            console.log(this.credentials.password);
            this.logService.authenticate(this.credentials, (() => { this.router.navigateByUrl('/'); }));            
            return false;
      }

}
