import {Component, Input} from '@angular/core';
import {Event} from '../event';

@Component({
  selector: 'app-event-list-item',
  imports: [],
  templateUrl: './event-list-item.component.html',
  styleUrl: './event-list-item.component.css'
})
export class EventListItemComponent {
  @Input() event!: Event;

}
