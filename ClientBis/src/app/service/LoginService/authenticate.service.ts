import { Injectable } from '@angular/core';
import { User } from 'src/app/model/user';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { finalize, catchError } from 'rxjs/operators';
import { Router } from '@angular/router';
@Injectable({
      providedIn: 'root'
})
export class AuthenticateService {
      user: User = new User();
      authenticated: boolean;

      constructor(private http: HttpClient, private router: Router) { }

      authenticate(credentials: User, callback): Observable<any> {
            const headers = {
                  'Authorization': 'Basic ' + btoa('myapp:secret'),
                  'Content-type': 'application/x-www-form-urlencoded'
            }
            const body = new HttpParams()
                  .set('username', credentials.email)
                  .set('password', credentials.password)
                  .set('grant_type', 'password');


            return this.http.post('http://localhost:8080/oauth/token', body, { headers: headers });
      }

      setLoggedUser(credentials: User) {
            this.http.get('http://localhost:8080/api/private/user?login=' + credentials.email +
                  "&password=" + credentials.password + "&access_token=" + localStorage.getItem("access_token"))
                  .subscribe(resp => {
                        console.log(resp);
                        localStorage.setItem("firstname", resp["firstname"]);
                        localStorage.setItem("lastname", resp["lastname"]);
                        localStorage.setItem("email", resp["email"]);
                        localStorage.setItem("login", resp["login"]);
                        localStorage.setItem("gender", resp["gender"]);
                        this.authenticated = true;
                        console.log(resp);
                        console.log(localStorage);
                  });

      }
      logout() {
            this.http.get('http://localhost:8080/api/private/logout&access_token='
                  + localStorage.getItem("access_token"), {}).
                  pipe(finalize(() => {
                        this.authenticated = false;
                        this.router.navigateByUrl('/');
                        localStorage.clear;
                  }), catchError(val => of("bbb hello"))).subscribe(
                        error => {
                              console.log("error logout");
                              this.authenticated = false;
                              localStorage.clear();
                              console.log(this.authenticated+" authentic");
                              this.router.navigateByUrl('');
                        }
                  );;
      }
}
