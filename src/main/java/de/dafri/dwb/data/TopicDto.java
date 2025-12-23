package de.dafri.dwb.data;

import de.dafri.dwb.data.model.EventModel;
import de.dafri.dwb.data.model.TopicDetailModel;
import de.dafri.dwb.data.repository.TopicRepository;
import de.dafri.dwb.domain.Event;
import de.dafri.dwb.domain.TopicDetail;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TopicDto {

    private final TopicRepository topicRepository;

    public TopicDto(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    public TopicDetail getByNr(String nr) {
        TopicDetailModel model = topicRepository.findByNr(nr);
        if (model == null) {
            return null;
        }

        List<EventModel> eventModels = topicRepository.findEventsByTopicId(model.id());

        List<Event> events = eventModels.stream().map(this::toEvent).toList();
        return new TopicDetail(model.nr(), model.title(), model.subtitle(), model.description(), model.text(), events);
    }

    private Event toEvent(EventModel eventModel) {
        return new Event(eventModel.nr(), eventModel.begin(), eventModel.end(), eventModel.place());
    }

}
