import { Component, OnInit } from '@angular/core';
import { LogService } from "../../Service/User/log.service";
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  
  credentials = { username: 'user1', password: '123' };

  constructor(private logService: LogService, private router: Router, private http: HttpClient) { }

  ngOnInit() {
  }

  login() {
    this.logService.authenticate(this.credentials,(() => { this.router.navigateByUrl('/'); })); 
    return false;
  }

}
