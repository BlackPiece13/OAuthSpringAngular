import { Component, OnInit } from '@angular/core';
import { AuthenticateService } from 'src/app/service/LoginService/authenticate.service';
import { UserService } from 'src/app/service/UserService/user.service';
@Component({
      selector: 'app-menu',
      templateUrl: './menu.component.html',
      styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {

      constructor(private authenticateService: AuthenticateService, private userService:UserService) { }

      ngOnInit() {
      }

      get logged() {
            return this.authenticateService.authenticated;
      }

      get isAdmin () : boolean {
            
            return this.authenticateService.isAdmin();
      }

      logout() {
            this.authenticateService.logout();
      }
}
