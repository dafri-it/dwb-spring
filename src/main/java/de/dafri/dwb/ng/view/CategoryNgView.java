package de.dafri.dwb.ng.view;

import de.dafri.dwb.view.CategoryView;
import de.dafri.dwb.view.SortLink;

import java.util.List;

public record CategoryNgView(CategoryView categoryView, List<SortLink> sortLinks) {
}
