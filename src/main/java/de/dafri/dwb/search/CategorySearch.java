package de.dafri.dwb.search;

import de.dafri.dwb.data.CategoryDto;
import de.dafri.dwb.domain.Category;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

import java.util.List;
import java.util.function.Supplier;

@Component
@ApplicationScope
public class CategorySearch extends Search<Category> {

    private final CategoryDto categoryDto;

    public CategorySearch(CategoryDto categoryDto) {
        super();
        this.categoryDto = categoryDto;
    }

    @Override
    protected String getElementKey(Category element) {
        return element.slug();
    }

    @Override
    protected Supplier<List<Category>> getDataSupplier() {
        return categoryDto::getCategoryList;
    }
}
