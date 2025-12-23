import {Component, OnInit} from '@angular/core';
import {Category} from '../category';
import {CategoryService} from '../category.service';
import {Topic} from '../topic';
import {CategoryTreeComponent} from '../category-tree/category-tree.component';
import {TopicListComponent} from '../topic-list/topic-list.component';
import {ActivatedRoute, Router} from '@angular/router';
import {PageableComponent} from '../pageable/pageable.component';
import {Pageable, PageableDefault} from '../pageable';

@Component({
  selector: 'app-category-view-page',
  imports: [
    CategoryTreeComponent,
    TopicListComponent,
    PageableComponent
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
  page: string = '';
  title: string = '';

  constructor(
    private categoryService: CategoryService,
    private currentRoute: ActivatedRoute,
    private router: Router) {
    this.pageable = new PageableDefault();
  }

  ngOnInit(): void {
    this.currentRoute.params.subscribe((params) => {
      this.query = params['query'];
      this.loadView(this.query, this.page);
    });
    this.currentRoute.queryParams.subscribe((params) => {
      this.page = params['page'];
      this.loadView(this.query, this.page);
    });
  }

  loadView(query: string, page: string) {
    if (!query) {
      return;
    }
    if (!page) {
      page = '0';
    }
    this.categoryService.view(query, page).subscribe(view => {
      if (query !== view.categoryView.category.slug) {
        this.router.navigate(['/category', view.categoryView.category.slug]);
        return;
      }
      this.href = '/category/' + view.categoryView.category.slug;
      this.title = view.categoryView.category.name;
      this.tree = view.categoryView.tree;
      this.topics = view.categoryView.topics;
      this.pageable = view.categoryView.pageable;
      this.pageCount = view.categoryView.pageCount;
    })
  }

}
