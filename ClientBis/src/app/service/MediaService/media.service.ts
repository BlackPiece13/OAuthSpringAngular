import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { Media } from "../../model/media";

@Injectable({
  providedIn: 'root'
})
export class MediaService {

  constructor(private http: HttpClient) { }

  getAudiosList(): Observable<any> {
    return this.http.get("http://localhost:8080/api/private/top10Audios?access_token=" + localStorage.getItem("access_token"));
  }
  getVideosList(): Observable<any> {
    return this.http.get("http://localhost:8080/api/private/top10Videos?access_token=" + localStorage.getItem("access_token"));
  }

  getMediasList(): Observable<any> {
    return this.http.get("http://localhost:8080/api/private/allMedias?access_token=" + localStorage.getItem("access_token"));
  }

  getMedia(id: string): Observable<any> {
    return this.http.get("http://localhost:8080/api/private/media/" + id + "?access_token=" + localStorage.getItem("access_token"));
  }

  deleteMedia(id: string): Observable<any> {
    return this.http.delete("http://localhost:8080/api/private/media/" + id + "?access_token=" + localStorage.getItem("access_token"));
  }

  updateMedia(media: Media): Observable<any> {
    const headers = {
      'Content-type': 'application/json'
    };
    return this.http.put("http://localhost:8080/api/private/media?access_token=" + localStorage.getItem("access_token"), media,
      { headers: headers });
  }

  addMedia(media: Media): Observable<any> {
    const headers = {
      'Content-type': 'application/json'
    };

    const body = {
      id: media.id, url: media.url, type: media.type,
      creationDate: media.creationDate, updateDate: media.updateDate, description: media.description,
      viewsNumber: media.viewsNumber, content: media.content
    };
    console.log("body");
    console.log(body);
    return this.http.post("http://localhost:8080/api/private/media?access_token=" + localStorage.getItem("access_token"), body,
      { headers: headers });
  }

}