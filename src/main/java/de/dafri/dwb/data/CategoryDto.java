package de.dafri.dwb.data;

import de.dafri.dwb.domain.Category;
import de.dafri.dwb.domain.Topic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class CategoryDto {

    private final static Logger logger = LoggerFactory.getLogger(CategoryDto.class);

    private List<Category> categoryTree = new ArrayList<>();
    private final CategoryRepository categoryRepository;
    private Map<Category, List<Topic>> categoryTopicMap;

    public CategoryDto(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
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

    public void init() {
        logger.info("Initializing categories...");

        this.categoryTree = new CategoryTreeCreator().createTree(
                categoryRepository.getCategoryModels(),
                categoryRepository.getCategoryRelationModels());

        this.categoryTopicMap = new TopicCreator().createTopics(
                categoryRepository.getEventModels(),
                categoryRepository.getTopicEventModels(),
                categoryRepository.getTopicModels(),
                categoryRepository.getCategoryTopicModels(),
                categoryTree);

        logger.info("Categories initialized");
    }
}