package de.dafri.dwb.frontend;

import de.dafri.dwb.view.CategoryView;
import de.dafri.dwb.view.SortLink;
import de.dafri.dwb.view.ViewService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CategoryController {

    private final ViewService viewService;
    private final CategoryPageableProvider categoryPageableProvider;
    private final CategorySortLinkProvider categorySortLinkProvider;

    public CategoryController(ViewService viewService, CategoryPageableProvider categoryPageableProvider, CategorySortLinkProvider categorySortLinkProvider) {
        this.viewService = viewService;
        this.categoryPageableProvider = categoryPageableProvider;
        this.categorySortLinkProvider = categorySortLinkProvider;
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

        if (!categoryPageableProvider.checkSorting(sortBy, sortOrder)) {
            sortBy = "none";
            sortOrder = "none";
        }

        Pageable pageable = categoryPageableProvider.getPageable(sortBy, sortOrder, page);

        List<SortLink> sortLinks = categorySortLinkProvider.getSortLinks(sortBy, sortOrder);

        CategoryView view = viewService.getCategoryView(nr, pageable);
        model.addAttribute("view", view);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortOrder", sortOrder);
        model.addAttribute("page", page);
        model.addAttribute("sortLinks", sortLinks);
        return "index";
    }
}
