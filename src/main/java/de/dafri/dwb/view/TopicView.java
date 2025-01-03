package de.dafri.dwb.view;

import java.util.List;

public record TopicView(List<CategoryTreeViewItem> tree, String nr, String title, String description, String text, List<EventViewItem> events) {
}
