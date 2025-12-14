import { Injectable } from '@angular/core';
import {TopicView} from './topic-view';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class TopicService {

  constructor(private http: HttpClient) {
  }

  view(nr: string) {
    return this.http.get<TopicView>(`http://localhost:8080/api/topic/${nr}`);
  }
}
