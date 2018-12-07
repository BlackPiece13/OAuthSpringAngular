import { Component, OnInit } from '@angular/core';
import { AuthenticateService } from 'src/app/service/LoginService/authenticate.service';
@Component({
      selector: 'app-menu',
      templateUrl: './menu.component.html',
      styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {

      constructor(private loginService: AuthenticateService) { }

      ngOnInit() {
      }

      get logged() {
            return this.loginService.authenticated;
      }

      logout() {
            console.log("hello logout");
            this.loginService.logout();
      }
}
