package de.dafri.dwb.data;

import de.dafri.dwb.data.model.CategoryTopicModel;
import de.dafri.dwb.data.model.EventModel;
import de.dafri.dwb.data.model.TopicEventModel;
import de.dafri.dwb.data.model.TopicModel;
import de.dafri.dwb.domain.Category;
import de.dafri.dwb.domain.Event;
import de.dafri.dwb.domain.Topic;

import java.util.*;
import java.util.stream.Collectors;

public class TopicListCreator {

    private final Map<Category, List<Topic>> categoryTopicMap = new HashMap<>();

    public Map<Category, List<Topic>> createTopics(List<EventModel> events, List<TopicEventModel> topicEvents, List<TopicModel> topicModels, List<CategoryTopicModel> categoryTopicModels, List<Category> categoryTree) {
        Map<Long, EventModel> eventIdMap = events.stream().collect(Collectors.toMap(EventModel::id, e -> e));
        Map<Long, TopicModel> topicById = topicModels.stream().collect(Collectors.toMap(TopicModel::id, t -> t));

        for (Category category : categoryTree) {
            fillCategoryTopics(category, categoryTopicModels, topicById, eventIdMap, topicEvents);

            for (Category child : category.children()) {
                fillCategoryTopics(child, categoryTopicModels, topicById, eventIdMap, topicEvents);
            }
        }

        return categoryTopicMap;
    }

    void fillCategoryTopics(Category category, List<CategoryTopicModel> categoryTopicModels, Map<Long, TopicModel> topicById, Map<Long, EventModel> eventIdMap, List<TopicEventModel> topicEvents) {
        List<Long> categoryIds = new ArrayList<>();
        categoryIds.add(category.id());
        category.children().forEach(c -> categoryIds.add(c.id()));

        List<Topic> topics = categoryTopicModels.stream()
                .filter(ct -> categoryIds.contains(ct.categoryId()))
                .sorted(Comparator.comparing(CategoryTopicModel::sort))
                .map(ct -> topicById.get(ct.topicId()))
                .distinct()
                .map(t -> new Topic(t.nr(), t.title(), t.subtitle(), t.description(), findEvents(t.id(), eventIdMap, topicEvents)))
                .toList();

        this.categoryTopicMap.put(category, topics);
    }

    List<Event> findEvents(Long topicId, Map<Long, EventModel> eventIdMap, List<TopicEventModel> topicEvents) {
        return topicEvents.stream()
                .filter(te -> te.topicId().equals(topicId))
                .sorted(Comparator.comparing(TopicEventModel::sort))
                .map(te -> eventIdMap.get(te.eventId()))
                .map(e -> new Event(e.nr(), e.begin(), e.end(), e.place())).toList();
    }
}
