import {Component, Input, OnInit} from '@angular/core';
import {Pageable} from '../pageable';
import {ActivatedRoute, Router, RouterLink} from '@angular/router';


@Component({
  selector: 'app-pageable',
  imports: [
    RouterLink
  ],
  templateUrl: './pageable.component.html',
  styleUrl: './pageable.component.css'
})
export class PageableComponent {

  @Input()
  href: string = '';

  constructor() {
  }
  //
  // ngOnInit(): void {
  //   this.route.url.subscribe((x) => this.href = "/" + x.join("/"));
  // }

  @Input()
  pageable: Pageable = {
    pageNumber: 0,
    paged: false,
    offset: 0,
    pageSize: 100,
    sort: {
      sorted: true
    }
  };

  @Input()
  pageCount = 0;

  get previous(): string | null {
    if (this.pageable.pageNumber === 0) {
      return null;
    }
    return (this.pageable.pageNumber - 1).toString();
  }

  get next(): string | null {
    if (this.pageable.pageNumber >= this.pageCount - 1) {
      return null;
    }
    return (this.pageable.pageNumber + 1).toString();
  }

  get pages(): number[] {
    let res = [];
    for (let i = 0; i < this.pageCount; i++) {
      res.push(i);
    }
    return res;
  }
}
