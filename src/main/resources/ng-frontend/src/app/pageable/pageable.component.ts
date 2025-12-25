import {Component, Input} from '@angular/core';
import {Pageable} from '../pageable';
import {Params, RouterLink} from '@angular/router';

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
  @Input() params!: Params;

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

  get nextParams() {
    return {...this.params, page: this.next};
  }

  get previousParams() {
    return {...this.params, page: this.previous};
  }

  pageParams(page: number) {
    return {...this.params, page: page.toString()};
  }
}
