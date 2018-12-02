import { Component, OnInit } from '@angular/core';
import { LogService } from 'src/app/Service/User/log.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private logService: LogService) { }

  ngOnInit() {
  }

  logged(): boolean {
    return this.logService.authenticated;
  }

}
