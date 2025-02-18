import { Routes } from '@angular/router';
import {CategoryIndexPageComponent} from './category-index-page/category-index-page.component';
import {CategoryViewPageComponent} from './category-view-page/category-view-page.component';

export const routes: Routes = [
  { path: '', component: CategoryIndexPageComponent },
  { path: 'category/:nr', component: CategoryViewPageComponent }
];
