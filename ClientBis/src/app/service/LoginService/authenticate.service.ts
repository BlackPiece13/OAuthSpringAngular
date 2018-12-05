import { Injectable } from '@angular/core';
import { User } from 'src/app/model/user';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
      providedIn: 'root'
})
export class AuthenticateService {
      user: User = new User();
      authenticated: boolean;
      constructor(private http: HttpClient) { }
      authenticate(credentials: User, callback) {
            console.log(credentials);
            const headers = {
                  'Authorization': 'Basic ' + btoa('myapp:secret'),
                  'Content-type': 'application/x-www-form-urlencoded'
            }
            const body = new HttpParams()
                  .set('username', credentials.email)
                  .set('password', credentials.password)
                  .set('grant_type', 'password');
            return this.http.post('http://localhost:8080/oauth/token', body, { headers: headers }).subscribe(response => {
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
            },
                  error => {
                        return '';
                  });
      }
}
