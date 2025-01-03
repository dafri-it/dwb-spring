@import de.dafri.dwb.view.EventViewItem
@import de.dafri.dwb.view.TopicListViewItem

@param TopicListViewItem topic

<li>
    <a href="/topic/${topic.nr()}">${topic.title()}</a>
    <ul>
        @for(EventViewItem event : topic.events())
            <li>${event.begin().toString()} ${event.place()}</li>
        @endfor
    </ul>
</li>