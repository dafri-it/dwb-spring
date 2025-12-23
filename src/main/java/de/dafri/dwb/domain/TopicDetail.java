package de.dafri.dwb.domain;

import de.dafri.dwb.util.Slugger;

import java.util.List;

public record TopicDetail(String nr, String title, String subtitle, String description, String text, List<Event> events) {

    public String slug() {
        String name = title;
        if (subtitle != null && !subtitle.isEmpty()) {
            name += " " + subtitle;
        }
        return Slugger.slug(name);
    }
}
