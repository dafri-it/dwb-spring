import {Component, Input} from '@angular/core';
import {Category} from '../category';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'app-category-tree',
  imports: [
    RouterLink
  ],
  templateUrl: './category-tree.component.html',
  styleUrl: './category-tree.component.css'
})
export class CategoryTreeComponent {

  @Input()
  tree: Category[] = [];

}
