@import de.dafri.dwb.view.CategoryTreeViewItem
@import de.dafri.dwb.view.EventViewItem
@import de.dafri.dwb.view.TopicListViewItem

@param de.dafri.dwb.view.TopicView view

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Start</title>
</head>
<body style="display: flex">
<aside>
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
    <h1>${view.category().name()}</h1>
    <ul>
        @for(TopicListViewItem topic: view.topics())
            <li>
                <a href="/topic/${topic.nr()}">${topic.title()}</a>
                <ul>
                    @for(EventViewItem event : topic.events())
                        <li>${event.begin().toString()} ${event.place()}</li>
                    @endfor
                </ul>
            </li>
        @endfor
    </ul>
</main>
</body>
</html>