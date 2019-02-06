import { Injectable, ErrorHandler, Injector, NgZone } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticateService } from '../LoginService/authenticate.service';


@Injectable({
  providedIn: 'root'
})
export class ErrorsHandlerService implements ErrorHandler {

  constructor(private injector: Injector, private zone: NgZone, private authService: AuthenticateService) { }
  handleError(error) {
    console.log(error);
    if (error.status == 401) {
      this.authService.logout();
      this.zone.run(() => { this.router.navigateByUrl('/login') });
    }
    //throw error;
  }
  /*
    lastUpdate = new Promise((resolve, reject) => {
      const date = new Date();
      resolve(date);
      setTimeout(
        () => { }
        , 2000
      );
    });*/

  public get router() {
    return this.injector.get(Router);
  }
}
