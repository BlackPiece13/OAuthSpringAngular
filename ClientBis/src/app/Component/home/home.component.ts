import { Component, OnInit } from '@angular/core';
import { MediaService } from 'src/app/service/MediaService/media.service';
@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  private _audios;
  constructor(private mediaService: MediaService) {
  }
  ngOnInit() {
    this.mediaService.getAudioList().subscribe(data => {
      this._audios
    });
  }
  get audios() {
    return this._audios;
  }
}
