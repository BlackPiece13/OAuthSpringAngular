import { Injectable, Input } from '@angular/core';
import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';
import { LoggedUser } from 'src/app/model/logged-user';
import { User } from 'src/app/Interface/user';
@Injectable({
      providedIn: 'root'
})
export class LogService {
      user: LoggedUser;
      authenticated: boolean;
      pt: LoggedUser;

      constructor(private http: HttpClient) { }
      authenticate(cred: LoggedUser, callback) {
            //let lg: LoggedUser = cred.;
            //lg.email="mail";
            //console.log(lg.email);
            this.user = new LoggedUser();
            console.log(cred);
            const headers = {
                  'Authorization': 'Basic ' + btoa('myapp:secret'),
                  'Content-type': 'application/x-www-form-urlencoded'
            }
            const body = new HttpParams()
                  .set('username', 'user1')
                  .set('password', '123')
                  .set('grant_type', 'password');
            this.http.post('http://localhost:8080/oauth/token', body, { headers: headers }).subscribe(response => {
                  if (response['access_token']) {
                        console.log("is authenticated");
                        if (response) {
                              this.user.email = response['username'];
                              this.user.password = response['password'];
                              this.user.access_token = response['access_token'];
                        }
                        this.authenticated = true;
                  } else {
                        console.log("not authenticated");
                        this.authenticated = false;
                  }
                  return callback && callback();
            });
      }
}
