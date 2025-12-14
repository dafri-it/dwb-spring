import {Category} from './category';

export interface TopicView {
  topicView: {
    description: string,
    events: [],
    nr: string,
    text: string,
    title: string,
    tree: Category[],
  }
}
