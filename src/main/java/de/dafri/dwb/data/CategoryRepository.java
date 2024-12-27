package de.dafri.dwb.data;

import java.util.List;

public interface CategoryRepository {

    List<CategoryTopicModel> getCategoryTopicModels();

    List<TopicModel> getTopicModels();

    List<TopicEventModel> getTopicEventModels();

    List<EventModel> getEventModels();

    List<CategoryRelationModel> getCategoryRelationModels();

    List<CategoryModel> getCategoryModels();
}
