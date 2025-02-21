package de.dafri.dwb.view;

import de.dafri.dwb.data.TopicDto;
import de.dafri.dwb.data.CategoryDto;
import de.dafri.dwb.domain.Category;
import de.dafri.dwb.domain.Event;
import de.dafri.dwb.domain.Topic;
import de.dafri.dwb.domain.TopicDetail;
import de.dafri.dwb.exception.CategoryNotFoundException;
import de.dafri.dwb.exception.TopicNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ViewService {

    private final CategoryDto categoryDto;
    private final TopicDto topicDto;

    public ViewService(CategoryDto categoryDto, TopicDto topicDto) {
        this.categoryDto = categoryDto;
        this.topicDto = topicDto;
    }

    public CategoryView getIndexView() {
        List<CategoryTreeViewItem> tree = getTree();
        return new CategoryView(tree, null, List.of(), null, 0);
    }

    public CategoryView getCategoryView(String categoryNr, Pageable pageable) {
        List<CategoryTreeViewItem> tree = getTree();
        Category category = categoryDto.getCategoryByNr(categoryNr);
        if (category == null) {
            throw new CategoryNotFoundException(categoryNr);
        }
        List<Topic> topics = categoryDto.getTopics(category);

        Sort.Order byName = pageable.getSort().getOrderFor("name");
        Sort.Order byDate = pageable.getSort().getOrderFor("date");
        Sort.Order byPlace = pageable.getSort().getOrderFor("place");

        List<TopicListViewItem> viewItems;

        if (byName != null) {
            viewItems = topics.stream()
                    .sorted((o1, o2) -> {
                                if (byName.isAscending()) {
                                    return o1.title().compareTo(o2.title());
                                } else {
                                    return o2.title().compareTo(o1.title());
                                }
                            }
                    ).map(this::toTopicItem)
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
                            if (byDate.isAscending()) {
                                return o1.begin().compareTo(o2.begin());
                            } else {
                                return o2.begin().compareTo(o1.begin());
                            }
                        }
                        else {
                            if (byPlace.isAscending()) {
                                return o1.place().compareTo(o2.place());
                            } else {
                                return o2.place().compareTo(o1.place());
                            }
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

    public TopicView getTopicView(String nr) {
        TopicDetail topicDetail = topicDto.getByNr(nr);
        if (topicDetail == null) {
            throw new TopicNotFoundException(nr);
        }

        List<EventViewItem> events = topicDetail.events().stream().map(this::toEventItem).toList();
        return new TopicView(getTree(), topicDetail.nr(), topicDetail.title(), topicDetail.description(), topicDetail.text(), events);
    }

    private List<CategoryTreeViewItem> getTree() {
        return categoryDto.getCategoryTree().stream().map(this::toTreeItem).toList();
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
