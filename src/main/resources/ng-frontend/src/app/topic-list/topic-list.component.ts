import {Component, Input} from '@angular/core';
import {Topic} from '../topic';
import {TopicListItemComponent} from '../topic-list-item/topic-list-item.component';

@Component({
  selector: 'app-topic-list',
  imports: [
    TopicListItemComponent
  ],
  templateUrl: './topic-list.component.html',
  styleUrl: './topic-list.component.css'
})
export class TopicListComponent {

  @Input()
  topics: Topic[] = [];

}
