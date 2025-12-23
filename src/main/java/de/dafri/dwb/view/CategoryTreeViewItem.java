package de.dafri.dwb.view;

import java.util.List;

public record CategoryTreeViewItem(String nr, String name, String slug, List<CategoryTreeViewItem> children) {

}
