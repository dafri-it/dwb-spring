import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {SearchResult} from './search-result';

@Injectable({
  providedIn: 'root'
})
export class SearchService {

  constructor(private http: HttpClient) { }

  search(query: string) {
    return this.http.get<SearchResult>(`http://localhost:8080/api/search/${query}`);
  }
}
