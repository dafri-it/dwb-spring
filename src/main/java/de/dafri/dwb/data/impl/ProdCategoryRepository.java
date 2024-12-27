package de.dafri.dwb.data.impl;

import de.dafri.dwb.data.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProdCategoryRepository implements CategoryRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProdCategoryRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<CategoryTopicModel> getCategoryTopicModels() {
        return jdbcTemplate.query(
                "SELECT rubrik, thema, prioritaet FROM rubrik_thema",
                (rs, rowNum) -> new CategoryTopicModel(
                        rs.getLong("rubrik"),
                        rs.getLong("thema"),
                        rs.getInt("prioritaet")
                ));
    }

    public List<TopicModel> getTopicModels() {
        return jdbcTemplate.query(
                "SELECT ID, nr, name, beschrLang, description FROM thema",
                (rs, rowNum) -> new TopicModel(
                        rs.getLong("ID"),
                        rs.getString("nr"),
                        rs.getString("name"),
                        rs.getString("beschrLang"),
                        rs.getString("description")
                ));
    }

    public List<TopicEventModel> getTopicEventModels() {
        return jdbcTemplate.query(
                "SELECT thema, termin, prioritaet FROM thema_termin",
                (rs, rowNum) -> new TopicEventModel(
                        rs.getLong("thema"),
                        rs.getLong("termin"),
                        rs.getInt("prioritaet")
                ));
    }

    public List<EventModel> getEventModels() {
        return jdbcTemplate.query(
                "SELECT ID, nr, von, bis, ort FROM termin",
                (rs, rowNum) -> new EventModel(
                        rs.getLong("ID"),
                        rs.getString("nr"),
                        rs.getDate("von"),
                        rs.getDate("bis"),
                        rs.getString("ort")
                ));
    }

    public List<CategoryRelationModel> getCategoryRelationModels() {
        return jdbcTemplate.query(
                "SELECT parent, child, prioritaet FROM rubrik_parent_child",
                (rs, rowNum) -> new CategoryRelationModel(
                        rs.getLong("parent"),
                        rs.getLong("child"),
                        rs.getInt("prioritaet")));
    }

    public List<CategoryModel> getCategoryModels() {
        return jdbcTemplate.query(
                "SELECT ID, nr, name, description FROM rubrik",
                (rs, rowNum) -> new CategoryModel(
                        rs.getLong("ID"),
                        rs.getString("nr"),
                        rs.getString("name"),
                        rs.getString("description")));
    }

}
