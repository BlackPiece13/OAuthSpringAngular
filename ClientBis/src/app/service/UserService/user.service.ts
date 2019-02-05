import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from 'src/app/model/user';
import { Observable } from 'rxjs';
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
      firstName: user.firstname, lastName: user.lastname, password: user.password,
      email: user.email, login: user.login
    };
    //JSON.stringify(user)
    return this.http.post('http://localhost:8080/api/public/register', body, { headers: headers });
  }

  add(user: User) {
    const headers = {
      'Content-type': 'application/json'
    }
    const body = {
      firstname: user.firstname, lastname: user.lastname, password: user.password,
      email: user.email, login: user.login, role: user.role, gender: user.gender
    };
    return this.http.post('http://localhost:8080/api/private/addUser?access_token=' +
      localStorage.getItem("access_token"), body, { headers: headers });
  }

  update(user: User) {
    const headers = {
      'Content-type': 'application/json'
    }

    const body = {
      id: user.id, firstname: user.firstname, lastname: user.lastname,
      email: user.email, login: user.login, role: user.role, gender: user.gender
    };
    return this.http.post('http://localhost:8080/api/private/updateUser?access_token='
      + localStorage.getItem('access_token'), body, { headers: headers });
  }

  getAllUsers(): Observable<any> {
    return this.http.get('http://localhost:8080/api/private/allUsers?access_token=' + localStorage.getItem("access_token"));
  }

  delete(id: number) {
    return this.http.get('http://localhost:8080/api/private/deleteUser/' + id + '?access_token=' + localStorage.getItem("access_token"));
  }

  getUser(id: number) {
    return this.http.get('http://localhost:8080/api/private/user/' + id + '?access_token=' + localStorage.getItem("access_token"));
  }

}
