import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { User } from 'src/app/model/user';
@Injectable({
      providedIn: 'root'
})
export class UserService {

      constructor(private http: HttpClient) { }

      register(user: User) {
            const headers = {
                  'Content-type': 'application/json'
            }

            const body = {
                  firstName: user.firstName, lastName: user.lastName, password: user.password,
                  email: user.email, login: user.login
            };
            console.log(body);
            //JSON.stringify(user)
            return this.http.post('http://localhost:8080/api/public/register', body, { headers: headers });
      }

}
