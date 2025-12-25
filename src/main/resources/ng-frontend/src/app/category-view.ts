import {Category} from './category';
import {Topic} from './topic';
import {Pageable} from './pageable';
import {SortLink} from './sort-link';

export interface CategoryView {
  categoryView: {
    category: Category,
    tree: Category[],
    topics: Topic[],
    pageable: Pageable,
    pageCount: number,
  },
  sortLinks: SortLink[],
}
