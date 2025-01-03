package de.dafri.dwb.frontend;

import de.dafri.dwb.view.CategoryView;
import de.dafri.dwb.view.SortLink;
import de.dafri.dwb.view.SortToggle;
import de.dafri.dwb.view.ViewService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CategoryController {

    private final ViewService viewService;

    public CategoryController(ViewService viewService) {
        this.viewService = viewService;
    }

    @GetMapping
    public String index(Model model) {
        CategoryView view = viewService.getIndexView();
        model.addAttribute("view", view);
        return "index";
    }

    @GetMapping("/category/{nr}")
    public String index(@PathVariable String nr,
                        @RequestParam(defaultValue = "none") String sortBy,
                        @RequestParam(defaultValue = "none") String sortOrder,
                        @RequestParam(defaultValue = "0") int page,
                        Model model) {

        if (!checkSorting(sortBy, sortOrder)) {
            sortBy = "none";
            sortOrder = "none";
        }

        Pageable pageable;
        if (!"none".equals(sortBy) && !"none".equals(sortOrder)) {
            Sort.Direction direction = ("asc".equals(sortOrder)) ? Sort.Direction.ASC : Sort.Direction.DESC;
            pageable = PageRequest.of(page, 10).withSort(direction, sortBy);
        } else {
            pageable = PageRequest.of(page, 10);
        }

        List<SortLink> sortLinks = getSortLinks(sortBy, sortOrder);

        CategoryView view = viewService.getCategoryView(nr, pageable);
        model.addAttribute("view", view);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortOrder", sortOrder);
        model.addAttribute("page", page);
        model.addAttribute("sortLinks", sortLinks);
        return "index";
    }

    private boolean checkSorting(String sortBy, String sortOrder) {
        if (!List.of("none", "name", "date", "place").contains(sortBy)) {
            return false;
        }

        if (!List.of(SortToggle.NONE, SortToggle.ASCENDING, SortToggle.DESCENDING).contains(sortOrder)) {
            return false;
        }

        return true;
    }

    private List<SortLink> getSortLinks(String sortBy, String sortOrder) {
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
