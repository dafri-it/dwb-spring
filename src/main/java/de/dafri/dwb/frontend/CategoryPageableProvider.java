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

        if (!List.of(SortToggle.NONE, SortToggle.ASCENDING, SortToggle.DESCENDING).contains(sortOrder)) {
            return false;
        }

        return true;
    }

    public Pageable getPageable(String sortBy, String sortOrder, int page) {
        Pageable pageable;
        if (!"none".equals(sortBy) && !"none".equals(sortOrder)) {
            Sort.Direction direction = ("asc".equals(sortOrder)) ? Sort.Direction.ASC : Sort.Direction.DESC;
            pageable = PageRequest.of(page, 10).withSort(direction, sortBy);
        } else {
            pageable = PageRequest.of(page, 10);
        }
        return pageable;
    }

}
