import {Category} from './category';
import {Event} from './event';

export interface TopicView {
  topicView: {
    description: string,
    events: Event[],
    nr: string,
    text: string,
    title: string,
    tree: Category[],
    slug: string,
  }
}
