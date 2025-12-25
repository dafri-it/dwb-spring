import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {CategoryView} from './category-view';
import {Params} from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  constructor(private http: HttpClient) {
  }

  index(): Observable<CategoryView> {
    return this.http.get<CategoryView>("http://localhost:8080/api/index");
  }

  view(query: string, queryParams: Params): Observable<CategoryView> {
    return this.http.get<CategoryView>(`http://localhost:8080/api/category/${query}`, {params: queryParams});
  }
}
