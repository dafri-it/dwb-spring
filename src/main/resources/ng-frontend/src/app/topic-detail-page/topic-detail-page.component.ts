import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {TopicService} from '../topic.service';
import {TopicView} from '../topic-view';
import {CategoryTreeComponent} from '../category-tree/category-tree.component';
import {Category} from '../category';
import {Event} from '../event';
import {EventListItemComponent} from '../event-list-item/event-list-item.component';


@Component({
  selector: 'app-topic-detail-page',
  imports: [
    CategoryTreeComponent,
    EventListItemComponent
  ],
  templateUrl: './topic-detail-page.component.html',
  styleUrl: './topic-detail-page.component.css'
})
export class TopicDetailPageComponent implements OnInit {
  private query!: string;
  tree!: Category[];
  title!: string;
  text!: string;
  events!: Event[];

  constructor(private topicService: TopicService, private currentRoute: ActivatedRoute, private router: Router) {
  }
    ngOnInit(): void {
      this.currentRoute.params.subscribe((params) => {
        this.query = params['query'];
        this.loadView(this.query);
      });
    }

  private loadView(query: string) {
    if (!query) {
      return;
    }

    this.topicService.view(query).subscribe((view: TopicView) => {
      if (view.topicView.slug !== query) {
        this.router.navigate(['/topic', view.topicView.slug]);
        return;
      }
      this.tree = view.topicView.tree;
      this.events = view.topicView.events;
      this.title = view.topicView.title;
      this.text = view.topicView.text;
    })
  }
}
