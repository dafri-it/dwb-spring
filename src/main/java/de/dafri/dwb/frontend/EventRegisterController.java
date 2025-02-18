package de.dafri.dwb.frontend;

import de.dafri.dwb.view.ParticipantForm;
import de.dafri.dwb.view.RegisterForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class EventRegisterController {

    @GetMapping("/event/{nr}/register")
    public String registerEvent(@PathVariable("nr") String nr, Model model) {
        model.addAttribute("form", new RegisterForm("David", "Fritsche", "daff88@gmail.com"));
        return "event-register";
    }

    @PostMapping("/event/register")
    public String registerEvent(RegisterForm form, Model model) {
        model.addAttribute("form", form);
        return "event-register";
    }

    @PostMapping("/event/register/{id}/participant")
    public String saveParticipant(@PathVariable("id") String id, ParticipantForm participantForm, Model model) {
        return "participant";
    }

    @PutMapping("/event/participant")
    public String addParticipant(Model model) {
        model.addAttribute("part", new ParticipantForm());
        return "participant";
    }

    @DeleteMapping("/event/participant/{id}")
    public String deleteParticipant(@PathVariable("id") String id, Model model) {
        return "participant";
    }
}
