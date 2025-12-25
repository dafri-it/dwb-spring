package de.dafri.dwb.frontend;

import de.dafri.dwb.view.SortToggle;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryPageableProvider {

    public boolean checkSorting(String sortBy, String sortOrder) {
        if (!List.of("none", "name", "date", "place").contains(sortBy)) {
            return false;
        }

        return List.of(SortToggle.NONE, SortToggle.ASCENDING, SortToggle.DESCENDING).contains(sortOrder);
    }

    public Pageable getPageable(String sortBy, String sortOrder, int page) {
        if ("none".equals(sortBy) || "none".equals(sortOrder)) {
            return PageRequest.of(page, 10);
        }
        Sort.Direction direction = ("asc".equals(sortOrder)) ? Sort.Direction.ASC : Sort.Direction.DESC;
        return PageRequest.of(page, 10).withSort(direction, sortBy);
    }

}
