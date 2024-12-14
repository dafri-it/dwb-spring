package de.dafri.dwb.view;

import java.util.List;

public record TopicView(List<CategoryTreeViewItem> tree, List<TopicListViewItem> topics) {
}
