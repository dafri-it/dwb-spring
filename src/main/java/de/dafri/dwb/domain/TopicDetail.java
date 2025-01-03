package de.dafri.dwb.domain;

import java.util.List;

public record TopicDetail(String nr, String title, String subtitle, String description, String text, List<Event> events) {
}
