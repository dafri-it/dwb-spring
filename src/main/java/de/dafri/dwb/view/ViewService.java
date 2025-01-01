package de.dafri.dwb.view;

import de.dafri.dwb.data.CategoryDto;
import de.dafri.dwb.domain.Category;
import de.dafri.dwb.domain.Event;
import de.dafri.dwb.domain.Topic;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
public class ViewService {

    private final CategoryDto categoryDto;

    public ViewService(CategoryDto categoryDto) {
        this.categoryDto = categoryDto;
    }

    public TopicView getIndexView() {
        List<CategoryTreeViewItem> tree = categoryDto.getCategoryTree().stream().map(this::toTreeItem).toList();
        return new TopicView(tree, null, List.of(), null);
    }

    public TopicView getCategoryView(String categoryNr, Pageable pageable) {
        List<CategoryTreeViewItem> tree = categoryDto.getCategoryTree().stream().map(this::toTreeItem).toList();
        Category category = categoryDto.getCategoryByNr(categoryNr);
        List<Topic> topics = categoryDto.getTopics(category);

        Sort.Order byName = pageable.getSort().getOrderFor("name");
        Sort.Order byDate = pageable.getSort().getOrderFor("date");
        Sort.Order byPlace = pageable.getSort().getOrderFor("place");

        Stream<TopicListViewItem> viewItemStream;

        if (byName != null) {
            viewItemStream = topics.stream()
                    .sorted((o1, o2) -> {
                                if (byName.isAscending()) {
                                    return o1.title().compareTo(o2.title());
                                } else {
                                    return o2.title().compareTo(o1.title());
                                }
                            }
                    ).map(this::toTopicItem);
        } else if (byDate != null || byPlace != null) {
            List<TopicEventItem> topicEventItems = new ArrayList<>();
            for (Topic topic : topics) {
                for (Event event : topic.events()) {
                    topicEventItems.add(
                            new TopicEventItem(topic.nr(), topic.title(), topic.subtitle(), topic.description(),
                                    event.nr(), event.begin(), event.end(), event.place()));
                }
            }

            viewItemStream = topicEventItems.stream()
                    .filter(ei -> ei.eventNr() != null)
                    .sorted((o1, o2) -> {
                        if (byDate != null) {
                            if (byDate.isAscending()) {
                                return o1.begin().compareTo(o2.begin());
                            } else {
                                return o2.begin().compareTo(o1.begin());
                            }
                        }
                        if (byPlace != null) {
                            if (byPlace.isAscending()) {
                                return o1.place().compareTo(o2.place());
                            } else {
                                return o2.place().compareTo(o1.place());
                            }
                        }
                        return 0;
                    })
                    .map(this::toTopicItem);
        } else {
            viewItemStream = topics.stream().map(this::toTopicItem);
        }

        List<TopicListViewItem> pagedTopics = viewItemStream.skip(pageable.getOffset()).limit(pageable.getPageSize()).toList();


        return new TopicView(tree, toTreeItem(category), pagedTopics, pageable);
    }

    private TopicListViewItem toTopicItem(Topic topic) {
        return new TopicListViewItem(topic.nr(), topic.title(), topic.subtitle(), topic.description(), topic.events().stream().map(this::toEventItem).toList());
    }

    private TopicListViewItem toTopicItem(TopicEventItem tei) {
        return new TopicListViewItem(tei.topicNr(), tei.title(), tei.subtitle(), tei.description(), List.of(new EventViewItem(tei.eventNr(), tei.begin(), tei.end(), tei.place())));
    }

    private EventViewItem toEventItem(Event event) {
        return new EventViewItem(event.nr(), event.begin(), event.end(), event.place());
    }

    private CategoryTreeViewItem toTreeItem(Category category) {
        return new CategoryTreeViewItem(category.nr(), category.name(), category.children().stream().map(this::toTreeItem).toList());
    }

}
