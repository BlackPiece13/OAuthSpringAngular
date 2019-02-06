import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/service/UserService/user.service';
import { FormControl, FormGroup, ValidatorFn, Validators, ValidationErrors } from '@angular/forms';
import { User } from 'src/app/model/user';
import { ViewChild, ElementRef } from '@angular/core';

@Component({
  selector: 'app-manage-users',
  templateUrl: './manage-users.component.html',
  styleUrls: ['./manage-users.component.css']
})
export class ManageUsersComponent implements OnInit {
  usersList = new Array();
  _credentials: User;
  formGroup: FormGroup;
  private _passwordBis: string;
  isSaveForm: boolean;
  private currentUserID: number; // id de l'entité mise à jour
  responseStatus: string;

  @ViewChild('closeModal') closeModal: ElementRef;

  constructor(private userService: UserService) { }

  ngOnInit() {
    this._credentials = new User();
    this.userService.getAllUsers().subscribe(res => {
      this.usersList = res;
    });
    this.formGroup = new FormGroup({
      'firstname': new FormControl(this._credentials.firstname,
        [Validators.required]
      ),
      'lastname': new FormControl(this._credentials.lastname,
        [Validators.required]),
      'login': new FormControl(this._credentials.login,
        [Validators.required, Validators.minLength(4)]),
      'password': new FormControl(this._credentials.password,
        [Validators.required, Validators.minLength(2)]),
      'passwordBis': new FormControl(this._passwordBis,
        [Validators.required, Validators.minLength(2)]),
      'email': new FormControl(this._credentials.email,
        [Validators.required, Validators.email]),
      'role': new FormControl(this._credentials.role,
        [Validators.required]),
      'gender': new FormControl(this._credentials.gender,
        [Validators.required])
    }, { validators: this.passwordsMatch });
  }

  deleteUser(id: number) {
    this.userService.delete(id).subscribe(() => {
      const index: number = this.usersList.indexOf(this.usersList.find(x => x.id == id));
      if (index !== -1) {
        this.usersList.splice(index, 1);
      }
      console.log("delete success");
    });
  }

  passwordsMatch: ValidatorFn = (control: FormGroup): ValidationErrors | null => {
    const password = control.get('password');
    const passwordBis = control.get('passwordBis');
    return password.value != passwordBis.value ? { 'passwordMatchError': true } : null;
  }

  addOrUpdate() {
    if (!this.formGroup.get("firstname").errors && !this.formGroup.get("lastname").errors &&
      !this.formGroup.get("login").errors && !this.formGroup.get("email").errors && !this.formGroup.get("gender").errors) {

      this._credentials.firstname = this.formGroup.get("firstname").value;
      this._credentials.lastname = this.formGroup.get("lastname").value;
      this._credentials.login = this.formGroup.get("login").value;
      this._credentials.email = this.formGroup.get("email").value;
      this._credentials.role = this.formGroup.get("role").value;
      this._credentials.gender = this.formGroup.get("gender").value;

      if (this.isSaveForm && !this.formGroup.get("password").errors) {
        this._credentials.password = this.formGroup.get("password").value;
        this.userService.add(this._credentials).subscribe(data => {
          console.log("ajout reussi");
          this.responseStatus = "OK";
          this.usersList.push(data);
          this.closeModal.nativeElement.click();
        }, err => {
          if (err.status == 400) {
            this.responseStatus = "ERROR";
            console.log("erreur ajout");
          };
        });
      }
      else {
        this._credentials.id = this.currentUserID;
        console.log("id entite " + this._credentials.id + "  /   " + this.currentUserID);
        this.userService.update(this._credentials).subscribe(data => {
          const index: number = this.usersList.indexOf(this.usersList.find(x => x.id == this.currentUserID));
          if (index !== -1) {
            this.usersList.splice(index, 1, data);
          }
          this.responseStatus = "OK";
          console.log("update reussi");
          this.sleep(1200).then(() => {
            this.closeModal.nativeElement.click();
          })
        }, err => {
          if (err.status == 400) {
            this.responseStatus = "ERROR";
            console.log("erreur update");
          };
        });
      }
    }
    else { console.log("erreur dans le formulaire"); }
  }

  sleep(time) {
    return new Promise((resolve) => setTimeout(resolve, time));
  }

  initForm(id: number) {
    this.responseStatus = '';
    this.isSaveForm = false;
    this.currentUserID = id;
    this.userService.getUser(id).subscribe(res => {
      this.formGroup.setValue({
        firstname: res['firstname'], lastname: res['lastname'], email: res['email'], role: res['role'],
        login: res['login'], password: '', passwordBis: '', gender: res['gender']
      });
      console.log(res);
    });
  }

  resetForm() {
    this.responseStatus = '';
    this.isSaveForm = true;
    this.formGroup.setValue({
      firstname: '', lastname: '', email: '', role: '', login: '', password: '', passwordBis: '', gender: ''
    });
  }

  get firstname() {
    return this.formGroup.get('firstname');
  }
  get lastname() {
    return this.formGroup.get('lastname');
  }
  get login() {
    return this.formGroup.get('login');
  }
  get password() {
    return this.formGroup.get('password');
  }
  get passwordBis() {
    return this.formGroup.get('passwordBis');
  }
  get email() {
    return this.formGroup.get('email');
  }
  get role() {
    return this.formGroup.get('role');
  }
  get gender() {
    return this.formGroup.get('gender');
  }

}
