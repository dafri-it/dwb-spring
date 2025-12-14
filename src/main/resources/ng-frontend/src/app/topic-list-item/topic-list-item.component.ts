import {Component, Input} from '@angular/core';
import {Topic} from '../topic';

@Component({
  selector: 'app-topic-list-item',
  imports: [],
  templateUrl: './topic-list-item.component.html',
  styleUrl: './topic-list-item.component.css'
})
export class TopicListItemComponent {
  @Input() topic!: Topic;

  protected readonly top = top;
}
