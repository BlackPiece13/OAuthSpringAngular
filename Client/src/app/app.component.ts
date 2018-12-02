import { Component } from '@angular/core';
import { LogService } from './Service/User/log.service';
import { HttpClient } from '@angular/common/http';
import { finalize } from 'rxjs/operators';
import { Router } from '@angular/router';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  constructor(private logService: LogService, private http: HttpClient, private router: Router) {
    this.logService.authenticate(undefined, undefined);
  }

  logout() {
    this.http.post('logout', {}).pipe(finalize(() => {
      this.logService.authenticated = false;
      this.router.navigateByUrl('/login');
    })).subscribe();
  }

}
