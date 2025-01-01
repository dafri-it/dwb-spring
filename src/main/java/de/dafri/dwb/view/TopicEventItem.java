package de.dafri.dwb.view;

import java.util.Date;

public record TopicEventItem(String topicNr, String title, String subtitle, String description, String eventNr, Date begin, Date end, String place) {
}
