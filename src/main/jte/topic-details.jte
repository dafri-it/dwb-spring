@import de.dafri.dwb.view.EventViewItem
@import de.dafri.dwb.view.TopicView
@param TopicView view

@template.base-layout(content = @`
    <aside>
        @template.category-tree(tree = view.tree(), current = null)
    </aside>
    <main>
        <h1>${view.title()}</h1>
        <p>${view.description()}</p>
        <ul>
            @for(EventViewItem event: view.events())
                <li>${event.begin().toString()} ${event.place()}</li>
            @endfor
        </ul>
        <div>$unsafe{view.text()}</div>
    </main>
`)