package de.dafri.dwb.ng.view;

import de.dafri.dwb.ng.search.CategorySearchResult;
import de.dafri.dwb.ng.search.TopicSearchResult;

import java.util.List;

public record SearchResultNgView(List<CategorySearchResult> categories, List<TopicSearchResult> topics) {
}
