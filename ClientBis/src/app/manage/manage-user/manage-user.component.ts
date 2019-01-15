import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/service/UserService/user.service';
import { Observable, of } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-manage-user',
  templateUrl: './manage-user.component.html',
  styleUrls: ['./manage-user.component.css']
})
export class ManageUserComponent implements OnInit {

  usersList: any;

  constructor(private userService: UserService, private http: HttpClient, ) { }

  ngOnInit() {
    this.http.get('http://localhost:8080/api/private/allUsers?access_token=' + localStorage.getItem("access_token"))
      .subscribe(resp => {
          this.usersList=resp;
      });
  }

  delete(id: number) {
    console.log(id);
    /*this.http.get('http://localhost:8080/api/private/deleteUser?access_token=' + localStorage.getItem("access_token")).
      subscribe(data => {
        console.log(data);
      });*/
  }

  update (id : number) {
    console.log("update");
  }

}
