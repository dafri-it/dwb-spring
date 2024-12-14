@import de.dafri.dwb.view.CategoryTreeViewItem
@import de.dafri.dwb.view.TopicListViewItem

@param de.dafri.dwb.view.TopicView view

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Start</title>
</head>
<body>
<aside style="float: left">
    <h1>Tree</h1>
    <ul>
        @for(CategoryTreeViewItem item: view.tree())
            <li><a href="/category/${item.nr()}">${item.name()}</a>
                <ul>
                    @for(CategoryTreeViewItem child: item.children())
                        <li><a href="/category/${child.nr()}">${child.name()}</a></li>
                    @endfor
                </ul>
            </li>
        @endfor
    </ul>
</aside>
<main>
    <h1>Main</h1>
    <ul>
        @for(TopicListViewItem topic: view.topics())
            <li>
                <a href="/topic/${topic.nr()}">${topic.title()}</a>
            </li>
        @endfor
    </ul>
</main>
</body>
</html>