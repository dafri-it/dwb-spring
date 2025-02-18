package de.dafri.dwb.ng;

import de.dafri.dwb.frontend.CategoryPageableProvider;
import de.dafri.dwb.frontend.CategorySortLinkProvider;
import de.dafri.dwb.ng.view.CategoryNgView;
import de.dafri.dwb.view.CategoryView;
import de.dafri.dwb.view.SortLink;
import de.dafri.dwb.view.ViewService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController()
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class FrontendController {

    private final ViewService viewService;
    private final CategoryPageableProvider categoryPageableProvider;
    private final CategorySortLinkProvider categorySortLinkProvider;

    public FrontendController(ViewService viewService, CategoryPageableProvider categoryPageableProvider, CategorySortLinkProvider categorySortLinkProvider) {
        this.viewService = viewService;
        this.categoryPageableProvider = categoryPageableProvider;
        this.categorySortLinkProvider = categorySortLinkProvider;
    }

    @GetMapping("/index")
    public CategoryNgView index() {
        CategoryView categoryView = viewService.getIndexView();
        List<SortLink> sortLinks = new ArrayList<>();
        return new CategoryNgView(categoryView, sortLinks);
    }

    @GetMapping("/category/{nr}")
    public CategoryNgView view(@PathVariable String nr,
                               @RequestParam(defaultValue = "none") String sortBy,
                               @RequestParam(defaultValue = "none") String sortOrder,
                               @RequestParam(defaultValue = "0") int page) {
        CategoryView categoryView = viewService.getCategoryView(nr, categoryPageableProvider.getPageable(sortBy, sortOrder, page));

        List<SortLink> sortLinks = categorySortLinkProvider.getSortLinks(sortBy, sortOrder);

        return new CategoryNgView(categoryView, sortLinks);
    }

}
