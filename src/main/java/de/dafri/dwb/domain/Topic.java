package de.dafri.dwb.domain;

import java.util.List;

public record Topic(String nr, String title, String subtitle, String description, List<Event> events) {
}
