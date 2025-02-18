import {Component, OnInit} from '@angular/core';
import {CategoryService} from '../category.service';
import {Category} from '../category';
import {RouterLink} from '@angular/router';
import {CategoryTreeComponent} from '../category-tree/category-tree.component';

@Component({
  selector: 'app-category-index-page',
  imports: [
    CategoryTreeComponent
  ],
  templateUrl: './category-index-page.component.html',
  styleUrl: './category-index-page.component.css'
})
export class CategoryIndexPageComponent implements OnInit {

  tree: Category[] = [];

  constructor(private categoryService: CategoryService) {
  }

  ngOnInit(): void {
    this.categoryService.index().subscribe(view => this.tree = view.categoryView.tree)
  }
}
