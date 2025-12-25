import {Component, OnInit} from '@angular/core';
import {Category} from '../category';
import {CategoryService} from '../category.service';
import {Topic} from '../topic';
import {CategoryTreeComponent} from '../category-tree/category-tree.component';
import {TopicListComponent} from '../topic-list/topic-list.component';
import {ActivatedRoute, Params, Router} from '@angular/router';
import {PageableComponent} from '../pageable/pageable.component';
import {Pageable, PageableDefault} from '../pageable';
import {SortableComponent} from '../sortable/sortable.component';
import {SortLink} from '../sort-link';

@Component({
  selector: 'app-category-view-page',
  imports: [
    CategoryTreeComponent,
    TopicListComponent,
    PageableComponent,
    SortableComponent
  ],
  templateUrl: './category-view-page.component.html',
  styleUrl: './category-view-page.component.css'
})
export class CategoryViewPageComponent implements OnInit {

  tree: Category[] = [];
  topics: Topic[] = [];
  pageable: Pageable;
  pageCount: number = 0;
  href: string = '';
  query: string = '';
  queryParams!: Params;
  title: string = '';
  sortLinks: SortLink[] = [];

  constructor(
    private categoryService: CategoryService,
    private currentRoute: ActivatedRoute,
    private router: Router) {
    this.pageable = new PageableDefault();
  }

  ngOnInit(): void {
    this.currentRoute.params.subscribe((params) => {
      this.query = params['query'];
      this.loadView(this.query, this.queryParams);
    });
    this.currentRoute.queryParams.subscribe((queryParams) => {
      this.queryParams = queryParams;
      this.loadView(this.query, this.queryParams);
    });
  }

  loadView(query: string, queryParams: Params) {
    if (!query) {
      return;
    }
    this.categoryService.view(query, queryParams).subscribe(view => {
      this.href = '/category/' + view.categoryView.category.slug;
      if (query !== view.categoryView.category.slug) {
        this.router.navigate([this.href]);
        return;
      }
      this.title = view.categoryView.category.name;
      this.tree = view.categoryView.tree;
      this.topics = view.categoryView.topics;
      this.pageable = view.categoryView.pageable;
      this.pageCount = view.categoryView.pageCount;
      this.sortLinks = view.sortLinks;
    })
  }

}
