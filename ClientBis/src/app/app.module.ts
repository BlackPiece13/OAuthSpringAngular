import { BrowserModule } from '@angular/platform-browser';
import { NgModule, ErrorHandler } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './Component/home/home.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { LoginComponent } from './Component/login/login.component';
import { MenuComponent } from './Component/menu/menu.component';
import { FooterComponent } from './Component/footer/footer.component';
import { RegisterComponent } from './Component/register/register.component';
import { ManageUsersComponent } from './Component/manage-users/manage-users.component';
import { ErrorsHandlerService } from './service/Exceptions/errors-handler.service';
import { ManageAudioComponent } from './Component/manage-audio/manage-audio.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    LoginComponent,
    MenuComponent,
    FooterComponent,
    RegisterComponent,
    ManageUsersComponent,
    ManageAudioComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule, HttpClientModule, ReactiveFormsModule
  ],
  providers: [ {provide : ErrorHandler, useClass: ErrorsHandlerService}],
  bootstrap: [AppComponent]
})
export class AppModule { }
