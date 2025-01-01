package de.dafri.dwb.frontend;

import de.dafri.dwb.view.TopicView;
import de.dafri.dwb.view.ViewService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CategoryController {

    private final ViewService viewService;

    public CategoryController(ViewService viewService) {
        this.viewService = viewService;
    }

    @GetMapping
    public String index(Model model) {
        TopicView view = viewService.getIndexView();
        model.addAttribute("view", view);
        return "index";
    }

    @GetMapping("/category/{nr}")
    public String index(@PathVariable String nr,
                        @RequestParam(defaultValue = "none") String sortBy,
                        @RequestParam(defaultValue = "none") String sortOrder,
                        @RequestParam(defaultValue = "0") int page,
                        Model model) {

        Pageable pageable;
        if (!"none".equals(sortBy) && !"none".equals(sortOrder)) {
            Sort.Direction direction = ("up".equals(sortOrder)) ? Sort.Direction.ASC : Sort.Direction.DESC;
            pageable = PageRequest.of(page, 10).withSort(direction, sortBy);
        } else {
            pageable = PageRequest.of(page, 10);
        }

        TopicView view = viewService.getCategoryView(nr, pageable);
        model.addAttribute("view", view);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortOrder", sortOrder);
        model.addAttribute("page", page);
        return "index";
    }
}
