import {Routes} from '@angular/router';
import {CategoryIndexPageComponent} from './category-index-page/category-index-page.component';
import {CategoryViewPageComponent} from './category-view-page/category-view-page.component';
import {TopicDetailPageComponent} from './topic-detail-page/topic-detail-page.component';

export const routes: Routes = [
  {path: '', component: CategoryIndexPageComponent},
  {path: 'category/:nr', component: CategoryViewPageComponent},
  {path: 'topic/:nr', component: TopicDetailPageComponent}
];
