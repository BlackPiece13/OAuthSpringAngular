export class User {
      private _firstName: string;
      private _lastName: string;
      private _email: string;
      private _password: string;
      private _login: string;
      private _access_token: string;
      
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

      get lastName() {
            return this._lastName;
      }
      set lastName(lastName: string) {
            this._lastName = lastName;
      }

      get firstName() {
            return this._firstName;
      }
      set firstName(firstName: string) {
            this._firstName = firstName;
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
