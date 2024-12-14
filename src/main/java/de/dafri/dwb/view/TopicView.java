package de.dafri.dwb.view;

import java.util.List;

public record TopicView(List<CategoryTreeViewItem> tree, CategoryTreeViewItem category, List<TopicListViewItem> topics) {
}
