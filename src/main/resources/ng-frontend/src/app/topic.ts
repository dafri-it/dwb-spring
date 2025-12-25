import {Event} from './event';

export interface Topic {
  uuid: string,
  nr: string,
  title: string,
  subTitle: string,
  description: string,
  slug: string,
  events: Event[],
}
