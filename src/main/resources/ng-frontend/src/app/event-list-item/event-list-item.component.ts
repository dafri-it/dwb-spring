import {Component, Input} from '@angular/core';
import {Event} from '../event';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'app-event-list-item',
  imports: [
    RouterLink
  ],
  templateUrl: './event-list-item.component.html',
  styleUrl: './event-list-item.component.css'
})
export class EventListItemComponent {
  @Input() event!: Event;

}
