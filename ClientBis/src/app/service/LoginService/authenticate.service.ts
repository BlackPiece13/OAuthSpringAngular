import { Injectable, OnDestroy } from '@angular/core';
import { User } from 'src/app/model/user';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { finalize, catchError } from 'rxjs/operators';
import { Router } from '@angular/router';
@Injectable({
  providedIn: 'root'
})
export class AuthenticateService implements OnDestroy {
  ngOnDestroy() {
    console.log("Auth service destroyed ");
  }
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
    this.http.get('http://localhost:8080/api/private/user?email=' + credentials.email +
      "&access_token=" + localStorage.getItem("access_token"))
      .subscribe(resp => {
        localStorage.setItem("firstname", resp["firstname"]);
        localStorage.setItem("lastname", resp["lastname"]);
        localStorage.setItem("email", resp["email"]);
        localStorage.setItem("login", resp["login"]);
        localStorage.setItem("gender", resp["gender"]);
        localStorage.setItem("role", resp['role']);
      });
  }
  logout() {
    this.http.get('http://localhost:8080/api/public/logout&token='
      + localStorage.getItem("access_token"), {}).
      pipe(finalize(() => {
        this.router.navigateByUrl('');
        localStorage.clear;
      }), catchError(() => of("log out"))).subscribe(
        error => {
          console.log("error logout");
          localStorage.clear();
          this.router.navigateByUrl('');
        },
        () => { console.log("success logout"); }
      );;
  }
  get authenticated() {
    return localStorage.getItem("access_token") ? true : false;
  }

  isAdmin(): boolean {
    return localStorage.getItem('role') == "ADMIN";
  }


}
