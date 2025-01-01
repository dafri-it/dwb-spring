package de.dafri.dwb.view;

import org.springframework.data.domain.Pageable;

import java.util.List;

public record TopicView(List<CategoryTreeViewItem> tree, CategoryTreeViewItem category, List<TopicListViewItem> topics,
                        Pageable pageable, int pageCount) {
}
