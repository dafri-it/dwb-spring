package de.dafri.dwb.data.repository;

import de.dafri.dwb.data.model.EventModel;
import de.dafri.dwb.data.model.TopicDetailModel;

import java.util.List;

public interface TopicRepository {

    List<TopicDetailModel> findAll();

    TopicDetailModel findByNr(String nr);

    List<EventModel> findEventsByTopicId(Long id);
}
