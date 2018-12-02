import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class LogService {

  authenticated: boolean;

  constructor(private http: HttpClient) { }



  authenticate(credentials, callback) {
    const headers = {
      'Authorization': 'Basic ' + btoa('myapp:secret'),
      'Content-type': 'application/x-www-form-urlencoded'
    }
    const body = new HttpParams()
      .set('username', 'user1')
      .set('password', '123')
      .set('grant_type', 'password');
    this.http.post('http://localhost:8083/oauth/token',body , { headers: headers }).subscribe(response => {
      if (response['name']) {
        console.log("is authenticated");
        this.authenticated = true;
      } else {
        console.log("not authenticated");
        this.authenticated = false;
      }
      return callback && callback();
    });
  }

}
