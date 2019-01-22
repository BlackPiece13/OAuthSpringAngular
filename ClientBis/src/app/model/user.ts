export class User {
  private _id;
  private _firstname: string;
  private _lastname: string;
  private _email: string;
  private _password: string;
  private _login: string;
  private _access_token: string;
  private _role: string;
  private _gender :string;

  get id() {
    return this._id;
  }
  set id(id: number) {
    this._id = id;
  }

  get gender() {
    return this._gender;
  }
  set gender(gender: string) {
    this._gender =gender;
  }

  get role() {
    return this._role;
  }
  set role(role: string) {
    this._role = role;
  }

  get login() {
    return this._login;
  }
  set login(login: string) {
    this._login = login;
  }

  get access_token() {
    return this._access_token;
  }
  set access_token(access_token: string) {
    this._access_token = access_token;
  }

  get lastname() {
    return this._lastname;
  }
  set lastname(lastname: string) {
    this._lastname = lastname;
  }

  get firstname() {
    return this._firstname;
  }
  set firstname(firstname: string) {
    this._firstname = firstname;
  }

  get password() {
    return this._password;
  }
  set password(password: string) {
    this._password = password;
  }

  get email() {
    return this._email;
  }
  set email(email: string) {
    this._email = email;
  }
}
