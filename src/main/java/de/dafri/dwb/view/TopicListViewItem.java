package de.dafri.dwb.view;

import java.util.List;

public record TopicListViewItem(String nr, String title, String subTitle, String slug, String description,
                                List<EventViewItem> events) {
}
