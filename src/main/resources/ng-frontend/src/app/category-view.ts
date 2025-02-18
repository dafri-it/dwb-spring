import {Category} from './category';
import {Topic} from './topic';
import {Pageable} from './pageable';

export interface CategoryView {
  categoryView: {
    tree: Category[],
    topics: Topic[],
    pageable: Pageable,
    pageCount: number,
  }
}
