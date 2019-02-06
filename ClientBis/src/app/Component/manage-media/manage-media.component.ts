import { Component, OnInit } from '@angular/core';
import { MediaService } from '../../service/MediaService/media.service';
import { Media } from '../../model/media';
import { FormGroup, FormControl, Validators } from '@angular/forms';
@Component({
  selector: 'app-manage-media',
  templateUrl: './manage-media.component.html',
  styleUrls: ['./manage-media.component.css']
})
export class ManageMediaComponent implements OnInit {
  private isSaveForm: boolean;
  _mediasList: Array<Media>;
  mediaCredentials: Media;
  mediaFormGroup: FormGroup;

  constructor(private mediaService: MediaService) {
  }

  ngOnInit() {
    this.mediaService.getMediasList().subscribe(data => { this._mediasList = <Array<Media>>data });
    this.mediaCredentials = new Media();
    this.mediaFormGroup = new FormGroup({
      "url": new FormControl(this.mediaCredentials.url, Validators.required),
      "type": new FormControl(this.mediaCredentials.type, Validators.required),
      "description": new FormControl(this.mediaCredentials.description, Validators.required)
    });
  }
  addOrUpdate() {
    console.log("addOrUpdate");
    this.mediaCredentials.type = this.mediaFormGroup.get('type').value;
    this.mediaCredentials.url = this.mediaFormGroup.get('url').value;
    this.mediaCredentials.description = this.mediaFormGroup.get('description').value;
    console.log(this.mediaCredentials);
    if (this.isSaveForm) {
      console.log("addOrUpdate save");
      this.mediaService.addMedia(this.mediaCredentials).subscribe(data => {
        this.mediasList.push(data); console.log("ajout reussi");
        console.log(data);
      });
    }
    else {
      this.mediaService.updateMedia(this.mediaCredentials).subscribe(data => {
        const index: number = this.mediasList.indexOf(this.mediasList.find(x => x.id == this.mediaCredentials.id));
        if (index !== -1) {
          this.mediasList.splice(index, 1, data);
        }
        console.log("MAJ Reussie");
      });
    }
  }

  deleteMedia(id: string) {
    this.mediaService.deleteMedia(id).subscribe(() => {
      const index: number = this.mediasList.indexOf(this.mediasList.find(x => x.id == id));
      if (index !== -1) {
        this.mediasList.splice(index, 1);
      }
      console.log("delete success");
    });
  }

  initForm(id: string) {
    this.isSaveForm = false;
    this.mediaService.getMedia(id).subscribe(data => {
      this.mediaCredentials = <Media>data; console.log(data);
      this.mediaFormGroup.setValue({
        type: this.mediaCredentials.type, url: this.mediaCredentials.url,
        description: this.mediaCredentials.description
      })
    });
  }

  resetForm() {
    this.mediaFormGroup.setValue({ url: '', type: '', description: '' });
    this.isSaveForm = true;
    this.mediaCredentials.id = '';
    this.mediaCredentials.url = '';
    this.mediaCredentials.type = '';
    this.mediaCredentials.creationDate = '';
    this.mediaCredentials.updateDate = '';
    this.mediaCredentials.content = '';
    this.mediaCredentials.description = '';
    this.mediaCredentials.viewsNumber = '';
  }

  get mediasList() {
    return this._mediasList;
  }
  get url() {
    return this.mediaFormGroup.get('url');
  }

  get type() {
    return this.mediaFormGroup.get('type');
  }

  get description() {
    return this.mediaFormGroup.get('description');
  }
}
