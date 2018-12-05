import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './Component/home/home.component';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { LoginComponent } from './Component/login/login.component';
import { MenuComponent } from './Component/menu/menu.component';
import { FooterComponent } from './Component/footer/footer.component';

@NgModule({
      declarations: [
            AppComponent,
            HomeComponent,
            LoginComponent,
            MenuComponent,
            FooterComponent
      ],
      imports: [
            BrowserModule,
            AppRoutingModule,
            FormsModule, HttpClientModule,
      ],
      providers: [],
      bootstrap: [AppComponent]
})
export class AppModule { }
