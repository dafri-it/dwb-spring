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

  constructor(private categoryService: CategoryService, private route: ActivatedRoute) {
    this.pageable = new PageableDefault();
  }

  ngOnInit(): void {
    this.route.params.subscribe((params) => {
      let nr = params['nr'];
      console.log(nr);
      this.loadView(nr);
    });
  }

  loadView(nr: string) {
    this.categoryService.view(nr).subscribe(view => {
      this.tree = view.categoryView.tree;
      this.topics = view.categoryView.topics;
      this.pageable = view.categoryView.pageable;
      this.pageCount = view.categoryView.pageCount;
      console.log(view.categoryView.pageCount);
    })
  }

}
