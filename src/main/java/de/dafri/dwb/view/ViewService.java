package de.dafri.dwb.view;

import de.dafri.dwb.data.CategoryDto;
import de.dafri.dwb.data.TopicDto;
import de.dafri.dwb.domain.Category;
import de.dafri.dwb.domain.Event;
import de.dafri.dwb.domain.Topic;
import de.dafri.dwb.domain.TopicDetail;
import de.dafri.dwb.exception.CategoryNotFoundException;
import de.dafri.dwb.exception.CategoryRedirectException;
import de.dafri.dwb.exception.TopicNotFoundException;
import de.dafri.dwb.exception.TopicRedirectException;
import de.dafri.dwb.search.CategorySearch;
import de.dafri.dwb.search.TopicSearch;
import de.dafri.dwb.util.Slugger;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ViewService {

    private final CategoryDto categoryDto;
    private final TopicDto topicDto;
    private final CategorySearch categorySearch;
    private final TopicSearch topicSearch;

    public ViewService(CategoryDto categoryDto, TopicDto topicDto, CategorySearch categorySearch, TopicSearch topicSearch) {
        this.categoryDto = categoryDto;
        this.topicDto = topicDto;
        this.categorySearch = categorySearch;
        this.topicSearch = topicSearch;
    }

    public CategoryView getIndexView() {
        List<CategoryTreeViewItem> tree = getTree();
        return new CategoryView(tree, null, List.of(), null, 0);
    }

    public CategoryView getCategoryView(String query, Pageable pageable, boolean api) {
        List<CategoryTreeViewItem> tree = getTree();
        Category category = categoryDto.getCategoryByNr(query);

        if (category == null) {
            List<Category> results = categorySearch.search(query);
            if (results.isEmpty()) {
                throw new CategoryNotFoundException(query);
            }
            category = results.getFirst();
        }

        if (category == null) {
            throw new CategoryNotFoundException(query);
        }

        if (!category.slug().equals(query)) {
            throw new CategoryRedirectException(category, api);
        }

        List<Topic> topics = categoryDto.getTopics(category);

        Sort.Order byName = pageable.getSort().getOrderFor("name");
        Sort.Order byDate = pageable.getSort().getOrderFor("date");
        Sort.Order byPlace = pageable.getSort().getOrderFor("place");

        List<TopicListViewItem> viewItems;

        if (byName != null) {
            viewItems = topics.stream()
                    .sorted((o1, o2) -> byName.isAscending() ? o1.title().compareTo(o2.title()) : o2.title().compareTo(o1.title()))
                    .map(this::toTopicItem)
                    .toList();
        } else if (byDate != null || byPlace != null) {
            List<TopicEventItem> topicEventItems = new ArrayList<>();
            for (Topic topic : topics) {
                for (Event event : topic.events()) {
                    topicEventItems.add(
                            new TopicEventItem(topic.nr(), topic.title(), topic.subtitle(), topic.description(),
                                    event.nr(), event.begin(), event.end(), event.place()));
                }
            }

            viewItems = topicEventItems.stream()
                    .filter(ei -> ei.eventNr() != null)
                    .sorted((o1, o2) -> {
                        if (byDate != null) {
                            return byDate.isAscending() ? o1.begin().compareTo(o2.begin()) : o2.begin().compareTo(o1.begin());
                        } else {
                            return byPlace.isAscending() ? o1.place().compareTo(o2.place()) : o2.place().compareTo(o1.place());
                        }
                    })
                    .map(this::toTopicItem)
                    .toList();
        } else {
            viewItems = topics.stream()
                    .map(this::toTopicItem)
                    .toList();
        }

        int total = viewItems.size();
        int pageCount = (int) Math.ceil((double) total / (double) pageable.getPageSize());

        List<TopicListViewItem> pagedTopics = viewItems.stream().skip(pageable.getOffset()).limit(pageable.getPageSize()).toList();

        return new CategoryView(tree, toTreeItem(category), pagedTopics, pageable, pageCount);
    }

    public TopicView getTopicView(String query, boolean api) {
        TopicDetail topicDetail = topicDto.getByNr(query);

        if (topicDetail == null) {
            List<Topic> topics = topicSearch.search(query);
            if (topics.isEmpty()) {
                throw new TopicNotFoundException(query);
            }

            topicDetail = topicDto.getByNr(topics.getFirst().nr());
        }

        if (topicDetail == null) {
            throw new TopicNotFoundException(query);
        }

        if (!topicDetail.slug().equals(query)) {
            throw new TopicRedirectException(topicDetail, api);
        }

        List<EventViewItem> events = topicDetail.events().stream().map(this::toEventItem).toList();
        return new TopicView(getTree(), topicDetail.nr(), topicDetail.title(), topicDetail.description(), topicDetail.text(), events, topicDetail.slug());
    }

    private List<CategoryTreeViewItem> getTree() {
        return categoryDto.getCategoryTree().stream().map(this::toTreeItem).toList();
    }

    private TopicListViewItem toTopicItem(Topic topic) {
        return new TopicListViewItem(UUID.randomUUID(), topic.nr(), topic.title(), topic.subtitle(), topic.slug(), topic.description(), topic.events().stream().map(this::toEventItem).toList());
    }

    private TopicListViewItem toTopicItem(TopicEventItem tei) {
        return new TopicListViewItem(UUID.randomUUID(), tei.topicNr(), tei.title(), tei.subtitle(), Slugger.slug(tei.title()), tei.description(), List.of(new EventViewItem(tei.eventNr(), tei.begin(), tei.end(), tei.place())));
    }

    private EventViewItem toEventItem(Event event) {
        return new EventViewItem(event.nr(), event.begin(), event.end(), event.place());
    }

    private CategoryTreeViewItem toTreeItem(Category category) {
        return new CategoryTreeViewItem(category.nr(), category.name(), category.slug(), category.children().stream().map(this::toTreeItem).toList());
    }
}
