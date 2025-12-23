import {CategorySearchResult} from './category-search-result';
import {TopicSearchResult} from './topic-search-result';

export interface SearchResult {
  categories: CategorySearchResult[],
  topics: TopicSearchResult[],
}
