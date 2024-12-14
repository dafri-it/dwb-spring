package de.dafri.dwb.data;

import de.dafri.dwb.domain.Category;
import de.dafri.dwb.domain.Event;
import de.dafri.dwb.domain.Topic;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class CategoryDto {

    private final JdbcTemplate jdbcTemplate;
    private final List<Category> categoryTree;
    private final Map<Category, List<Topic>> categoryTopicMap = new HashMap<>();

    public CategoryDto(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.categoryTree = new ArrayList<>();
    }

    public List<Category> getCategoryTree() {
        if (categoryTree.isEmpty()) {
            init();
        }
        return Collections.unmodifiableList(categoryTree);
    }

    public Category getCategoryByNr(String nr) {
        for (Category category : categoryTree) {
            if (category.nr().equals(nr)) {
                return category;
            }
            for (Category child : category.children()) {
                if (child.nr().equals(nr)) {
                    return child;
                }
            }
        }

        return null;
    }

    public List<Topic> getTopics(Category category) {
        return Collections.unmodifiableList(categoryTopicMap.getOrDefault(category, new ArrayList<>()));
    }


    // TODO refactor
    void init() {
        List<CategoryModel> categoryModels = jdbcTemplate.query(
                "SELECT ID, nr, name, description FROM rubrik",
                (rs, rowNum) -> new CategoryModel(
                        rs.getLong("ID"),
                        rs.getString("nr"),
                        rs.getString("name"),
                        rs.getString("description")));

        List<CategoryRelationModel> relationModels = jdbcTemplate.query(
                "SELECT parent, child, prioritaet FROM rubrik_parent_child",
                (rs, rowNum) -> new CategoryRelationModel(
                        rs.getLong("parent"),
                        rs.getLong("child"),
                        rs.getInt("prioritaet")));

        Map<Long, CategoryModel> categoryIdlMap = categoryModels.stream()
                .collect(Collectors.toMap(CategoryModel::id, categoryModel -> categoryModel));

        Map<Long, List<CategoryRelationModel>> relationsTree = new HashMap<>();
        for (CategoryRelationModel relationModel : relationModels) {
            if (!relationsTree.containsKey(relationModel.parentId())) {
                relationsTree.put(relationModel.parentId(), new ArrayList<>());
            }
            relationsTree.get(relationModel.parentId()).add(relationModel);
        }

        // sort
        Map<Long, List<CategoryRelationModel>> sortedRelationsTree = new HashMap<>();
        for (Map.Entry<Long, List<CategoryRelationModel>> entry : relationsTree.entrySet()) {
            List<CategoryRelationModel> list = entry.getValue().stream().sorted(Comparator.comparing(CategoryRelationModel::sort)).toList();
            sortedRelationsTree.put(entry.getKey(), list);
        }

        // find root elements
        List<CategoryModel> rootElements = categoryModels.stream()
                .filter(c -> c.nr().equals("0") || c.nr().startsWith("00"))
                .toList();

        for (CategoryModel rootElement : rootElements) {
            List<CategoryRelationModel> childRelations = sortedRelationsTree.getOrDefault(rootElement.id(), new ArrayList<>());

            List<Category> children = new ArrayList<>();
            for (CategoryRelationModel childRelation : childRelations) {
                CategoryModel child = categoryIdlMap.get(childRelation.childId());
                if (child != null) {
                    children.add(new Category(child.id(), child.nr(), child.name(), child.description(), List.of()));
                }
            }

            categoryTree.add(new Category(rootElement.id(), rootElement.nr(), rootElement.name(), rootElement.description(), children));
        }

        List<EventModel> events = jdbcTemplate.query(
                "SELECT ID, nr, von, bis, ort FROM termin",
                (rs, rowNum) -> new EventModel(
                        rs.getLong("ID"),
                        rs.getString("nr"),
                        rs.getDate("von"),
                        rs.getDate("bis"),
                        rs.getString("ort")
                ));

        List<TopicEventModel> topicEvents = jdbcTemplate.query(
                "SELECT thema, termin, prioritaet FROM thema_termin",
                (rs, rowNum) -> new TopicEventModel(
                        rs.getLong("thema"),
                        rs.getLong("termin"),
                        rs.getInt("prioritaet")
                ));

        Map<Long, EventModel> eventIdMap = new HashMap<>();
        for (EventModel event : events) {
            eventIdMap.put(event.id(), event);
        }

        List<TopicModel> topicModels = jdbcTemplate.query(
                "SELECT ID, nr, name, beschrLang, description FROM thema",
                (rs, rowNum) -> new TopicModel(
                        rs.getLong("ID"),
                        rs.getString("nr"),
                        rs.getString("name"),
                        rs.getString("beschrLang"),
                        rs.getString("description")
                ));

        Map<Long, TopicModel> topicById = topicModels.stream()
                .collect(Collectors.toMap(TopicModel::id, t -> t));

        List<CategoryTopicModel> categoryTopicModels = jdbcTemplate.query(
                "SELECT rubrik, thema, prioritaet FROM rubrik_thema",
                (rs, rowNum) -> new CategoryTopicModel(
                        rs.getLong("rubrik"),
                        rs.getLong("thema"),
                        rs.getInt("prioritaet")
                ));

        for (Category category : categoryTree) {
            fillCategoryTopics(category, categoryTopicModels, topicById, eventIdMap, topicEvents);

            for (Category child : category.children()) {
                fillCategoryTopics(child, categoryTopicModels, topicById, eventIdMap, topicEvents);
            }
        }
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
