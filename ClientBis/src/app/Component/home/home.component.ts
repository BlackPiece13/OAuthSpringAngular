import { Component, OnInit } from '@angular/core';
import { MediaService } from 'src/app/service/MediaService/media.service';
import { Media } from "../../model/media";
@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  private _audiosList: Array<Media>;
  private _videosList: Array<Media>;
  constructor(private mediaService: MediaService) {
  }

  ngOnInit() {
    this.mediaService.getAudiosList().subscribe(data => { this._audiosList = <Array<Media>>data });
    this.mediaService.getVideosList().subscribe(data => { console.log(data); this._videosList = <Array<Media>>data });
  }

  get audiosList() {
    return this._audiosList;
  }
  get videosList() {
    return this._videosList;
  }
}
