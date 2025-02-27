package de.dafri.dwb.search;

import de.dafri.dwb.data.CategoryDto;
import de.dafri.dwb.domain.Category;
import de.dafri.dwb.domain.Topic;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

import java.util.List;

@Component
@ApplicationScope
public class Search {

    private final CategoryDto categoryDto;

    private final Index<Category> categoryIndex = new Index<>();
    private final Index<Topic> topicIndex = new Index<>();

    public Search(CategoryDto categoryDto) {
        this.categoryDto = categoryDto;
    }

    public List<Category> searchCategory(String query) {
        return categoryIndex.search(query);
    }

    @PostConstruct
    private void init() {
        addCategories(categoryIndex);
        addTopics(topicIndex);
    }

    private void addTopics(Index<Topic> index) {
        List<Topic> topics = categoryDto.getTopicList();
        for (Topic topic : topics) {
            addTopic(index, topic);
        }
    }

    private void addTopic(Index<Topic> index, Topic topic) {
        index.put(topic.slug(), topic);
    }

    private void addCategories(Index<Category> index) {
        List<Category> categories = categoryDto.getCategoryList();

        for (Category category : categories) {
            addCategory(index, category);
        }
    }

    private void addCategory(Index<Category> index, Category category) {
        index.put(category.slug(), category);
    }

    public List<Topic> searchTopic(String query) {
        return topicIndex.search(query);
    }
}
