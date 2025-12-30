package de.dafri.dwb.data.repository.impl;

import de.dafri.dwb.data.model.EventModel;
import de.dafri.dwb.data.model.TopicDetailModel;
import de.dafri.dwb.data.repository.TopicRepository;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProdTopicRepository implements TopicRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ProdTopicRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<TopicDetailModel> findAll() {
        return jdbcTemplate.query(
                "SELECT ID, nr, name, beschrLang, description, beschrAusf FROM thema",
                (rs, i) -> new TopicDetailModel(
                        rs.getLong("ID"),
                        rs.getString("nr"),
                        rs.getString("name"),
                        rs.getString("beschrLang"),
                        rs.getString("description"),
                        rs.getString("beschrAusf")
                )
        );
    }

    @Override
    public TopicDetailModel findByNr(String nr) {
        return jdbcTemplate.query(
                "SELECT ID, nr, name, beschrLang, description, beschrAusf FROM thema WHERE nr = :nr",
                new MapSqlParameterSource().addValue("nr", nr),
                (rs, rowNum) ->
                        new TopicDetailModel(
                                rs.getLong("ID"),
                                rs.getString("nr"),
                                rs.getString("name"),
                                rs.getString("beschrLang"),
                                rs.getString("description"),
                                rs.getString("beschrAusf")
                        )
        ).stream().findFirst().orElse(null);
    }

    @Override
    public List<EventModel> findEventsByTopicId(Long id) {
        return jdbcTemplate.query(
                "SELECT ID, nr, von, bis, ort FROM termin t JOIN thema_termin tt ON t.id = tt.termin and tt.thema = :id ORDER BY tt.prioritaet",
                new MapSqlParameterSource().addValue("id", id),
                (rs, rowNum) -> new EventModel(
                        rs.getLong("ID"),
                        rs.getString("nr"),
                        rs.getDate("von"),
                        rs.getDate("bis"),
                        rs.getString("ort")
                ));
    }
}
