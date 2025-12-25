package de.dafri.dwb.view;

import java.util.List;
import java.util.UUID;

public record TopicListViewItem(UUID uuid, String nr, String title, String subTitle, String slug, String description,
                                List<EventViewItem> events) {
}
