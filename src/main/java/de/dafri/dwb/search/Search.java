package de.dafri.dwb.search;

import de.dafri.dwb.data.CategoryDto;
import de.dafri.dwb.domain.Category;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

import java.util.List;

@Component
@ApplicationScope
public class Search {

    private final CategoryDto categoryDto;

    private final Index<Category> categoryIndex = new Index<>();

    public Search(CategoryDto categoryDto) {
        this.categoryDto = categoryDto;
    }

    @PostConstruct
    private void init() {
        addCategories(categoryIndex);
    }

    public List<Category> searchCategory(String query) {
        return categoryIndex.search(query);
    }

    private void addCategories(Index<Category> index) {
        List<Category> categories = categoryDto.getCategoryList();

        for (Category category : categories) {
            addCategory(index, category);
        }
    }

    private void addCategory(Index<Category> index, Category category) {
        index.put(category.name(), category);
    }
}
