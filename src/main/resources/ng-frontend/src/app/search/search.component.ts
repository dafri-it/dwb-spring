import { Component } from '@angular/core';
import {FormsModule} from '@angular/forms';
import {SearchService} from '../search.service';
import {SearchResult} from '../search-result';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'app-search',
  imports: [
    FormsModule,
    RouterLink
  ],
  templateUrl: './search.component.html',
  styleUrl: './search.component.css'
})
export class SearchComponent {
  searchTerm!: string;
  result!: SearchResult;

  constructor(private searchService: SearchService) { }

  search() {
    if (!this.searchTerm || this.searchTerm.trim().length === 0) {
      this.result = {
        categories: [],
        topics: []
      };
      return;
    }
    this.searchService.search(this.searchTerm).subscribe(res => this.result = res);
 }

 isOpen() {
    if (!this.result) {
      return false;
    }
    if (!this.result.categories) {
      return false;
    }
    if (!this.result.topics) {
      return false;
    }
    return !(this.result.categories.length === 0 && this.result.topics.length === 0);
 }

  close() {

  }
}
