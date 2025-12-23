package de.dafri.dwb.data;

import de.dafri.dwb.data.model.TopicModel;
import de.dafri.dwb.data.repository.CategoryRepository;
import de.dafri.dwb.domain.Category;
import de.dafri.dwb.domain.Topic;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CategoryDto {

    private final static Logger logger = LoggerFactory.getLogger(CategoryDto.class);

    private List<Category> categoryTree = new ArrayList<>();
    private final CategoryRepository categoryRepository;
    private Map<Category, List<Topic>> categoryTopicMap;
    private List<Category> categoryList;
    private List<Topic> topicList;

    public CategoryDto(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @PostConstruct
    private void start() {
        init();
    }

    public List<Category> getCategoryTree() {
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

    public void init() {
        logger.info("Initializing categories...");

        this.categoryTree = new CategoryTreeCreator().createTree(
                categoryRepository.getCategoryModels(),
                categoryRepository.getCategoryRelationModels());

        this.categoryTopicMap = new TopicListCreator().createTopics(
                categoryRepository.getEventModels(),
                categoryRepository.getTopicEventModels(),
                categoryRepository.getTopicModels(),
                categoryRepository.getCategoryTopicModels(),
                categoryTree);

        this.categoryList = flatTree(categoryTree);
        this.topicList = createTopicList(categoryRepository.getTopicModels());

        logger.info("Categories initialized");
    }

    private List<Topic> createTopicList(List<TopicModel> topicModels) {
        return topicModels.stream()
                .map(tm -> new Topic(tm.nr(), tm.title(), tm.subtitle(), tm.description(), List.of()))
                .toList();
    }

    private List<Category> flatTree(List<Category> categoryTree) {
        Set<Category> categorySet = new HashSet<>();
        for (Category category : categoryTree) {
            categorySet.add(category);
            categorySet.addAll(category.children());
        }
        return List.copyOf(categorySet);
    }

    public List<Category> getCategoryList() {
        return Collections.unmodifiableList(categoryList);
    }

    public List<Topic> getTopicList() {
        return topicList;
    }
}