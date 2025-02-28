package de.dafri.dwb.search;

import de.dafri.dwb.data.CategoryDto;
import de.dafri.dwb.domain.Topic;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

import java.util.List;
import java.util.function.Supplier;

@Component
@ApplicationScope
public class TopicSearch extends Search<Topic> {

    private final CategoryDto categoryDto;

    public TopicSearch(CategoryDto categoryDto) {
        this.categoryDto = categoryDto;
    }

    @Override
    protected Supplier<List<Topic>> getDataSupplier() {
        return categoryDto::getTopicList;
    }

    @Override
    protected String getElementKey(Topic element) {
        return element.slug();
    }
}
