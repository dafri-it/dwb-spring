package de.dafri.dwb;

import de.dafri.dwb.data.CategoryDto;
import de.dafri.dwb.domain.Category;
import de.dafri.dwb.domain.Topic;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TopicWebTest {

    @Autowired
    TestRestTemplate restTemplate;


    @Value("${local.server.port}")
    private int port;

//    @Autowired
//    TopicDto topicDto;

    @Autowired
    CategoryDto categoryDto;

    @Test
    public void testTopic() {
        Topic firstTopic = getFirstTopicNr();
        String url = "http://localhost:" + port + "/topic/" + firstTopic.nr();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertTrue(response.getBody().contains(firstTopic.title()));
    }

    @Test
    public void test_topic_not_found() {
        String url = "http://localhost:" + port + "/topic/123123";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    private Topic getFirstTopicNr() {
        List<Category> tree = categoryDto.getCategoryTree();
        for (Category category : tree) {
            List<Topic> topics = categoryDto.getTopics(category);
            Topic first = topics.getFirst();
            if (first != null) {
                return first;
            }
        }

        throw new IllegalStateException("No topic found");
    }
}
