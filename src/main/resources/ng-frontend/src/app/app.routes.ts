import {Routes} from '@angular/router';
import {CategoryIndexPageComponent} from './category-index-page/category-index-page.component';
import {CategoryViewPageComponent} from './category-view-page/category-view-page.component';
import {TopicDetailPageComponent} from './topic-detail-page/topic-detail-page.component';
import {RegisterEventPageComponent} from './register-event-page/register-event-page.component';

export const routes: Routes = [
  {path: '', component: CategoryIndexPageComponent},
  {path: 'category/:query', component: CategoryViewPageComponent},
  {path: 'topic/:query', component: TopicDetailPageComponent},
  {path: 'event-register/:nr', component: RegisterEventPageComponent},
];
