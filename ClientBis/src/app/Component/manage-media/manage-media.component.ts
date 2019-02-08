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
      "title": new FormControl(this.mediaCredentials.title, Validators.required),
      "url": new FormControl(this.mediaCredentials.url, Validators.required),
      "type": new FormControl(this.mediaCredentials.type, Validators.required),
      "description": new FormControl(this.mediaCredentials.description, Validators.required)
    });
  }
  addOrUpdate() {
    console.log("addOrUpdate");
    this.mediaCredentials.title = this.mediaFormGroup.get("title").value;
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
        title: this.mediaCredentials.title,
        type: this.mediaCredentials.type, url: this.mediaCredentials.url,
        description: this.mediaCredentials.description
      })
    });
  }

  resetForm() {
    this.isSaveForm = true;
    this.mediaCredentials = {
      id: '', title: '', url: '', type: '', creationDate: '', updateDate: '', content: '', description: '',
      viewsNumber: ''
    };
    this.mediaFormGroup.setValue({ title: '', url: '', type: '', description: '' });
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
  get title() {
    return this.mediaFormGroup.get("title");
  }
}
