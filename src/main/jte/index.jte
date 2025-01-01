@import de.dafri.dwb.view.CategoryTreeViewItem
@import de.dafri.dwb.view.EventViewItem
@import de.dafri.dwb.view.SortLink
@import de.dafri.dwb.view.TopicListViewItem
@import java.util.List

@param de.dafri.dwb.view.TopicView view
@param String sortBy
@param String sortOrder
@param List<SortLink> sortLinks

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
            <li>
                @if(view.category() != null && view.category().equals(item))
                    &gt;
                @endif
                <a href="/category/${item.nr()}">${item.name()}</a>
                <ul>
                    @for(CategoryTreeViewItem child: item.children())

                        <li>
                            @if(view.category() != null && view.category().equals(child))
                                &gt;
                            @endif
                            <a href="/category/${child.nr()}">${child.name()}</a>
                        </li>
                    @endfor
                </ul>
            </li>
        @endfor
    </ul>
</aside>
<main>
    @if(view.category() == null)
        Please select a category
    @else
        <h1>${view.category().name()}</h1>
        
        @for(SortLink link : sortLinks)
            <a href="/category/${view.category().nr()}${link.link()}">${link.title()} ${link.direction()}</a>
        @endfor

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
        <a href="/category/${view.category().nr()}?page=${view.pageable().previousOrFirst().getPageNumber()}&sortBy=${sortBy}&sortOrder=${sortOrder}">Previous</a>

        @for(int index = 0; index < view.pageCount(); index++)
            <a href="/category/${view.category().nr()}?page=${index}&sortBy=${sortBy}&sortOrder=${sortOrder}">${index + 1}</a>
        @endfor

        @if(view.pageable().getPageNumber() < view.pageCount() -1)
            <a href="/category/${view.category().nr()}?page=${view.pageable().next().getPageNumber()}&sortBy=${sortBy}&sortOrder=${sortOrder}">Next</a>
        @endif
    @endif
</main>
</body>
</html>