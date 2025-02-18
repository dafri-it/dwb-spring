import {Component, Input} from '@angular/core';
import {Topic} from '../topic';

@Component({
  selector: 'app-topic-list',
  imports: [],
  templateUrl: './topic-list.component.html',
  styleUrl: './topic-list.component.css'
})
export class TopicListComponent {

  @Input()
  topics: Topic[] = [];

}
