import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, ValidatorFn, Validators, ValidationErrors } from '@angular/forms';
import { User } from 'src/app/model/user';
import { UserService } from 'src/app/service/UserService/user.service';
import { Router } from '@angular/router';
@Component({
      selector: 'app-register',
      templateUrl: './register.component.html',
      styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
      _credentials: User;
      _passwordBis: string;
      registerForm: FormGroup;
      condition: boolean = true;
      firstNameClass: string = "form-control";
      constructor(private http: HttpClient, private userService: UserService, private router: Router) { }

      ngOnInit() {
            this.firstNameClass = "form-control ";
            this._credentials = new User();
            this.registerForm = new FormGroup({
                  'firstName': new FormControl(this._credentials.firstName,
                        [Validators.required]

                  ),
                  'lastName': new FormControl(this._credentials.lastName,
                        [Validators.required]),
                  'login': new FormControl(this._credentials.login,
                        [Validators.required, Validators.minLength(4)]),
                  'password': new FormControl(this._credentials.password,
                        [Validators.required, Validators.minLength(2)]),
                  'passwordBis': new FormControl(this._passwordBis,
                        [Validators.required, Validators.minLength(2)]),
                  'email': new FormControl(this._credentials.email,
                        [Validators.required, Validators.email]),
            }, { validators: this.passwordsMatch });
      }
      //!(this.registerForm.get('password') == this.registerForm.get('passwordBis'));
      passwordsMatch: ValidatorFn = (control: FormGroup): ValidationErrors | null => {
            const password = control.get('password');
            const passwordBis = control.get('passwordBis');
            return password.value != passwordBis.value ? { 'passwordMatchError': true } : null;
      };

      register() {
            this._credentials.firstName = this.registerForm.get("firstName").value
            this._credentials.lastName = this.registerForm.get("lastName").value
            this._credentials.login = this.registerForm.get("login").value
            this._credentials.password = this.registerForm.get("password").value
            this._credentials.email = this.registerForm.get("email").value;
            console.log(this._credentials);
            if (!this.registerForm.get("firstName").errors && !this.registerForm.get("lastName").errors &&
                  !this.registerForm.get("login").errors && !this.registerForm.get("password").errors &&
                  !this.registerForm.get("email").errors) {
                  this.userService.register(this._credentials).subscribe(data => {
                        this.router.navigateByUrl('login');
                  });
            }
      }
      get firstName() {
            return this.registerForm.get('firstName');
      }
      get lastName() {
            return this.registerForm.get('lastName');
      }
      get login() {
            return this.registerForm.get('login');
      }
      get password() {
            return this.registerForm.get('password');
      }
      get passwordBis() {
            return this.registerForm.get('password');
      }
      get email() {
            return this.registerForm.get('email');
      }
}
