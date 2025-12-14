import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {TopicService} from '../topic.service';
import {TopicView} from '../topic-view';
import {CategoryTreeComponent} from '../category-tree/category-tree.component';
import {Category} from '../category';


@Component({
  selector: 'app-topic-detail-page',
  imports: [
    CategoryTreeComponent
  ],
  templateUrl: './topic-detail-page.component.html',
  styleUrl: './topic-detail-page.component.css'
})
export class TopicDetailPageComponent implements OnInit {
  private nr!: string;
  tree!: Category[];
  title!: string;
  text!: string;

  constructor(private topicService: TopicService, private route: ActivatedRoute) {
  }
    ngOnInit(): void {
      this.route.params.subscribe((params) => {
        this.nr = params['nr'];
        this.loadView(this.nr);
      });
    }

  private loadView(nr: string) {
    if (!nr) {
      return;
    }

    this.topicService.view(nr).subscribe((view: TopicView) => {
      this.tree = view.topicView.tree;
      this.title = view.topicView.title;
      this.text = view.topicView.text;
    })
  }
}
