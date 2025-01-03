package de.dafri.dwb.data.repository;

import de.dafri.dwb.data.model.CategoryRelationModel;
import de.dafri.dwb.data.model.CategoryTopicModel;
import de.dafri.dwb.data.model.CategoryModel;
import de.dafri.dwb.data.model.EventModel;
import de.dafri.dwb.data.model.TopicEventModel;
import de.dafri.dwb.data.model.TopicModel;

import java.util.List;

public interface CategoryRepository {

    List<CategoryTopicModel> getCategoryTopicModels();

    List<TopicModel> getTopicModels();

    List<TopicEventModel> getTopicEventModels();

    List<EventModel> getEventModels();

    List<CategoryRelationModel> getCategoryRelationModels();

    List<CategoryModel> getCategoryModels();
}
