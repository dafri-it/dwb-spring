package de.dafri.dwb.frontend;

import de.dafri.dwb.view.TopicView;
import de.dafri.dwb.view.ViewService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
    public String index(@PathVariable String nr, Model model) {
        TopicView view = viewService.getCategoryView(nr);
        model.addAttribute("view", view);
        return "index";
    }
}
