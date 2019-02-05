import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class MediaService {

  constructor(private http: HttpClient) { }

  getAudiosList(): Observable<any> {
    return this.http.get("http://localhost:8080/api/private/top10Audios?access_token=" + localStorage.getItem("access_token"));
  }
  getVideosList() {
    return this.http.get("http://localhost:8080/api/private/top10Videos?access_token=" + localStorage.getItem("access_token"));
  }

  getMediasList () {
    return this.http.get("http://localhost:8080/api/private/allMedias?access_token=" + localStorage.getItem("access_token"));
  }
}