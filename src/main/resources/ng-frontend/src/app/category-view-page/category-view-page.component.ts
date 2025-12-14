import {Component, OnInit} from '@angular/core';
import {Category} from '../category';
import {CategoryService} from '../category.service';
import {Topic} from '../topic';
import {CategoryTreeComponent} from '../category-tree/category-tree.component';
import {TopicListComponent} from '../topic-list/topic-list.component';
import {ActivatedRoute} from '@angular/router';
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
  nr: string = '';
  page: string = '';

  constructor(private categoryService: CategoryService, private route: ActivatedRoute) {
    this.pageable = new PageableDefault();
  }

  ngOnInit(): void {
    this.route.params.subscribe((params) => {
      this.nr = params['nr'];
      this.loadView(this.nr, this.page);
    });
    this.route.queryParams.subscribe((params) => {
      this.page = params['page'];
      this.loadView(this.nr, this.page);
    });
  }

  loadView(nr: string, page: string) {
    if (!nr) {
      return;
    }
    if (!page) {
      page = '0';
    }
    this.categoryService.view(nr, page).subscribe(view => {
      this.href = '/category/' + nr;
      this.tree = view.categoryView.tree;
      this.topics = view.categoryView.topics;
      this.pageable = view.categoryView.pageable;
      this.pageCount = view.categoryView.pageCount;
    })
  }

}
