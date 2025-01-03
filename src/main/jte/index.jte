@import de.dafri.dwb.view.SortLink
@import de.dafri.dwb.view.TopicListViewItem
@import java.util.List

@param de.dafri.dwb.view.CategoryView view
@param String sortBy
@param String sortOrder
@param List<SortLink> sortLinks

@template.base-layout(content = @`
    <aside>
        @template.category-tree(tree = view.tree(), current = view.category())
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
                    @template.topic-item(topic = topic)
                @endfor
            </ul>

            @if(view.pageCount() > 1)
                @if(view.pageable().hasPrevious())
                    <a href="/category/${view.category().nr()}?page=${view.pageable().previousOrFirst().getPageNumber()}&sortBy=${sortBy}&sortOrder=${sortOrder}">Previous</a>
                @else
                    Previous
                @endif

                @for(int index = 0; index < view.pageCount(); index++)
                    <a href="/category/${view.category().nr()}?page=${index}&sortBy=${sortBy}&sortOrder=${sortOrder}">${index + 1}</a>
                @endfor

                @if(view.pageable().getPageNumber() < view.pageCount() -1)
                    <a href="/category/${view.category().nr()}?page=${view.pageable().next().getPageNumber()}&sortBy=${sortBy}&sortOrder=${sortOrder}">Next</a>
                @else
                    Next
                @endif
            @endif
        @endif
    </main>
`)