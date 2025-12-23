package de.dafri.dwb.ng.search;

import de.dafri.dwb.domain.Category;
import de.dafri.dwb.domain.Topic;
import de.dafri.dwb.ng.view.SearchResultNgView;
import de.dafri.dwb.search.CategorySearch;
import de.dafri.dwb.search.TopicSearch;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {

    private final CategorySearch categorySearch;
    private final TopicSearch topicSearch;

    public SearchService(CategorySearch categorySearch, TopicSearch topicSearch) {
        this.categorySearch = categorySearch;
        this.topicSearch = topicSearch;
    }

    public SearchResultNgView search(String query) {
        List<Category> categories = categorySearch.search(query);
        List<Topic> topics = topicSearch.search(query);

        List<CategorySearchResult> categoryResults = categories.stream()
                .map(c -> new CategorySearchResult(c.nr(), c.name(), c.slug()))
                .toList();
        List<TopicSearchResult> topicResults = topics.stream()
                .map(t -> new TopicSearchResult(t.nr(), t.title(), t.subtitle(), t.slug()))
                .toList();

        return new SearchResultNgView(categoryResults, topicResults);
    }

}
