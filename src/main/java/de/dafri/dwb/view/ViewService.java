package de.dafri.dwb.view;

import de.dafri.dwb.data.CategoryDto;
import de.dafri.dwb.domain.Category;
import de.dafri.dwb.domain.Event;
import de.dafri.dwb.domain.Topic;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ViewService {

    private final CategoryDto categoryDto;

    public ViewService(CategoryDto categoryDto) {
        this.categoryDto = categoryDto;
    }

    public TopicView getIndexView() {
        List<CategoryTreeViewItem> tree = categoryDto.getCategoryTree().stream().map(this::toTreeItem).toList();
        return new TopicView(tree, null, List.of());
    }

    public TopicView getCategoryView(String categoryNr) {
        List<CategoryTreeViewItem> tree = categoryDto.getCategoryTree().stream().map(this::toTreeItem).toList();
        Category category = categoryDto.getCategoryByNr(categoryNr);
        List<TopicListViewItem> topics = categoryDto.getTopics(category).stream().map(this::toTopicItem).toList();
        return new TopicView(tree, toTreeItem(category), topics);
    }

    private TopicListViewItem toTopicItem(Topic topic) {
        return new TopicListViewItem(topic.nr(), topic.title(), topic.subtitle(), topic.description(), topic.events().stream().map(this::toEventItem).toList());
    }

    private EventViewItem toEventItem(Event event) {
        return new EventViewItem(event.nr(), event.begin(), event.end(), event.place());
    }

    private CategoryTreeViewItem toTreeItem(Category category) {
        return new CategoryTreeViewItem(category.nr(), category.name(), category.children().stream().map(this::toTreeItem).toList());
    }

}
