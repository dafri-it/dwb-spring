package de.dafri.dwb.frontend;

import de.dafri.dwb.view.TopicView;
import de.dafri.dwb.view.ViewService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class TopicController {

    private final ViewService viewService;

    public TopicController(ViewService viewService) {
        this.viewService = viewService;
    }

    @GetMapping("/topic/{nr}")
    public String topic(@PathVariable String nr, Model model) {
        TopicView view = viewService.getTopicView(nr);
        model.addAttribute("view", view);
        return "topic-details";
    }
}
