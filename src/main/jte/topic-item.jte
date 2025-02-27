@import de.dafri.dwb.view.EventViewItem
@import de.dafri.dwb.view.TopicListViewItem

@param TopicListViewItem topic

<li>
    <a href="/topic/${topic.slug()}">${topic.title()}</a>
    <ul>
        @for(EventViewItem event : topic.events())
            <li>${event.begin().toString()} ${event.place()} <a href="/event/${topic.nr()}/register">Register</a> </li>
        @endfor
    </ul>
</li>