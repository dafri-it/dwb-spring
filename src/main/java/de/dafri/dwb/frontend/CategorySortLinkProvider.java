package de.dafri.dwb.frontend;

import de.dafri.dwb.view.SortLink;
import de.dafri.dwb.view.SortToggle;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategorySortLinkProvider {

    public List<SortLink> getSortLinks(String sortBy, String sortOrder) {
        SortToggle current = new SortToggle(sortBy, sortOrder);

        return List.of(
                createLink(current, current.by("name"), "Name"),
                createLink(current, current.by("date"), "Date"),
                createLink(current, current.by("place"), "Place")
        );
    }

    private SortLink createLink(SortToggle current, SortToggle newValue, String title) {
        return new SortLink("?sortBy=" + newValue.sortBy() + "&sortOrder=" + newValue.sortOrder(),
                title, getDirection(current, newValue));
    }

    private String getDirection(SortToggle current, SortToggle newValue) {
        if (current.sortBy().equals(newValue.sortBy())) {
            return current.sortOrder();
        } else {
            return SortToggle.NONE;
        }
    }


}
