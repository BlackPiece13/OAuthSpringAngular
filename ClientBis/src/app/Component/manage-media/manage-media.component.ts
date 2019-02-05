import { Component, OnInit } from '@angular/core';
import { MediaService } from '../../service/MediaService/media.service';
import { Media } from '../../model/media'
@Component({
  selector: 'app-manage-media',
  templateUrl: './manage-media.component.html',
  styleUrls: ['./manage-media.component.css']
})
export class ManageMediaComponent implements OnInit {

  private _mediasList: Array<Media>;
  constructor(private mediaService: MediaService) {
  }

  ngOnInit() {
    this.mediaService.getMediasList().subscribe(data => { this._mediasList = <Array<Media>>data });
  }
  get mediasList() {
    return this._mediasList;
  }

}
