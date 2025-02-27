@import de.dafri.dwb.view.CategoryTreeViewItem
@import java.util.List
@param List<CategoryTreeViewItem> tree
@param CategoryTreeViewItem current

<style>
    .tree {
        .current {
            background: yellow;
        }

        li ul {
            display: none;
        }

        li.current ul {
            display: block;
        }
    }
</style>
<ul class="tree">
    @for(CategoryTreeViewItem item: tree)
        <li class="${(current != null && (current.equals(item) || item.children().contains(current))) ? "current" : null}">
            <a href="/category/${item.slug()}">${item.name()}</a>
            <ul>
                @for(CategoryTreeViewItem child: item.children())
                    <li  class="${(current != null && current.equals(child)) ? "current" : null}">
                        <a href="/category/${child.slug()}">${child.name()}</a>
                    </li>
                @endfor
            </ul>
        </li>
    @endfor
</ul>
