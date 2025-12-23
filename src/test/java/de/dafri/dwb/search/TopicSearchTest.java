package de.dafri.dwb.search;

import de.dafri.dwb.domain.Topic;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TopicSearchTest {

    @Autowired
    private TopicSearch topicSearch;

    @Test
    public void search_beamtenrecht() {
        search("beamtenrecht-grundlagen", "beamtenrecht-grundlagen-kompakt");
    }

    @Test
    public void search_beamtenrcht() {
        search("bemtenrcht grndlagen", "beamtenrecht-grundlagen-kompakt");
    }

    @Test
    public void search_ausland() {
        search("ausland", "auslandsreisekostenrecht-alle-bundeslaender-bund");
    }

    @Test
    public void search_bundesreise() {
        search("bundesreise", "das-bundesreisekostenrecht-grundlagen");
    }

    private void search(String query, String expected) {
        List<Topic> result = topicSearch.search(query);
        assertNotNull(result);
        assertFalse(result.isEmpty(), "no results");
        assertEquals(expected, result.getFirst().slug());
    }

}
