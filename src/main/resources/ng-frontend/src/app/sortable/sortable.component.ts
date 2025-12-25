import {Component, Input} from '@angular/core';
import {Pageable} from '../pageable';
import {SortLink} from '../sort-link';
import {Params, RouterLink} from '@angular/router';

@Component({
  selector: 'app-sortable',
  imports: [
    RouterLink

  ],
  templateUrl: './sortable.component.html',
  styleUrl: './sortable.component.css'
})
export class SortableComponent {
  @Input() href!: string;
  @Input() pageable!: Pageable;
  @Input() sortLinks!: SortLink[]
  @Input() queryParams!: Params;

  params(link: SortLink): Params {
    return {sortBy: link.sortBy, sortOrder: link.sortOrder};
  }

}
