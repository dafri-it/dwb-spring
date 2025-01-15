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
public class CategoryWebTest {

    static final int PAGE_SIZE = 10;
    private final static String RECHT_NR = "002";
    private final static String CATEGORY_NOT_EXISTING = "0011";
    private static final String AKTUELLES_NR = "0";

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    CategoryDto categoryDto;

    @Value("${local.server.port}")
    private int port;

    @Test
    public void categoryIndex() {
        String url = "http://localhost:" + port + "/";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());

        List<Category> tree = categoryDto.getCategoryTree();

        for (Category category : tree) {
            Assertions.assertTrue(response.getBody().contains(category.name()));
        }
    }

    @Test
    public void category_not_found() {
        String url = "http://localhost:" + port + "/category/" + CATEGORY_NOT_EXISTING;
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void category_recht_contains_children() {
        String url = "http://localhost:" + port + "/category/" + RECHT_NR;
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        List<Category> tree = categoryDto.getCategoryTree();
        for (Category category : tree) {
            if (RECHT_NR.equals(category.nr())) {
                Assertions.assertFalse(category.children().isEmpty());

                for (Category child : category.children()) {
                    Assertions.assertTrue(response.getBody().contains(child.name()));
                }
            }
        }
    }

    @Test
    public void category_recht_first_child_is_found() {
        Category recht = categoryDto.getCategoryByNr(RECHT_NR);
        Category first = recht.children().getFirst();
        Assertions.assertNotNull(first);

        String url = "http://localhost:" + port + "/category/" + first.nr();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());

        Assertions.assertTrue(response.getBody().contains(first.name()));
    }

    @Test
    public void category_recht_has_topics() {
        String url = "http://localhost:" + port + "/category/" + RECHT_NR;
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());

        Category category = categoryDto.getCategoryByNr(RECHT_NR);
        List<Topic> topics = categoryDto.getTopics(category);

        for (int i = 0; i < topics.size(); i++) {
            if (i >= PAGE_SIZE) {
                break;
            }

            Assertions.assertTrue(response.getBody().contains(topics.get(i).title()));
        }
    }

    @Test
    public void category_recht_has_pages() {
        String url = "http://localhost:" + port + "/category/" + RECHT_NR;
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());

        Category category = categoryDto.getCategoryByNr(RECHT_NR);
        List<Topic> topics = categoryDto.getTopics(category);

        int pages = topics.size() / PAGE_SIZE;

        for (int i = 0; i < pages + 1; i++) {
            Assertions.assertTrue(response.getBody().contains("/category/" + RECHT_NR + "?page=" + i));
        }
    }
    
    @Test
    public void category_aktuelles_has_no_pages() {
        String url = "http://localhost:" + port + "/category/" + AKTUELLES_NR;

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());

        Category aktuelles = categoryDto.getCategoryByNr(AKTUELLES_NR);
        List<Topic> topics = categoryDto.getTopics(aktuelles);

        Assertions.assertTrue(topics.size() < PAGE_SIZE);

        Assertions.assertFalse(response.getBody().contains("/category/" + AKTUELLES_NR + "?page"));
    }

    @Test
    public void category_recht_has_sorting_links() {
        String url = "http://localhost:" + port + "/category/" + RECHT_NR;
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertTrue(response.getBody().contains("/category/" + RECHT_NR + "?sortBy=name"));
        Assertions.assertTrue(response.getBody().contains("/category/" + RECHT_NR + "?sortBy=date"));
        Assertions.assertTrue(response.getBody().contains("/category/" + RECHT_NR + "?sortBy=place"));
    }

    @Test
    public void category_sort_by_name_asc() {
        String url = "http://localhost:" + port + "/category/" + RECHT_NR + "?sortBy=name&sortOrder=asc";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertTrue(response.getBody().contains("Name asc"));
        Assertions.assertTrue(response.getBody().contains("Date none"));
        Assertions.assertTrue(response.getBody().contains("Place none"));

        Assertions.assertTrue(response.getBody().contains("?sortBy=name&amp;sortOrder=desc"));
    }

    @Test
    public void category_sort_by_name_desc() {
        String url = "http://localhost:" + port + "/category/" + RECHT_NR + "?sortBy=name&sortOrder=desc";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertTrue(response.getBody().contains("Name desc"));
        Assertions.assertTrue(response.getBody().contains("Date none"));
        Assertions.assertTrue(response.getBody().contains("Place none"));

        Assertions.assertTrue(response.getBody().contains("?sortBy=name&amp;sortOrder=none"));
    }

    @Test
    public void category_sort_by_name_none() {
        String url = "http://localhost:" + port + "/category/" + RECHT_NR + "?sortBy=name&sortOrder=none";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertTrue(response.getBody().contains("Name none"));
        Assertions.assertTrue(response.getBody().contains("Date none"));
        Assertions.assertTrue(response.getBody().contains("Place none"));

        Assertions.assertTrue(response.getBody().contains("?sortBy=name&amp;sortOrder=asc"));
    }

    @Test
    public void category_sort_by_date_asc() {
        String url = "http://localhost:" + port + "/category/" + RECHT_NR + "?sortBy=date&sortOrder=asc";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertTrue(response.getBody().contains("Name none"));
        Assertions.assertTrue(response.getBody().contains("Date asc"));
        Assertions.assertTrue(response.getBody().contains("Place none"));

        Assertions.assertTrue(response.getBody().contains("?sortBy=date&amp;sortOrder=desc"));
    }

    @Test
    public void category_sort_by_date_desc() {
        String url = "http://localhost:" + port + "/category/" + RECHT_NR + "?sortBy=date&sortOrder=desc";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertTrue(response.getBody().contains("Name none"));
        Assertions.assertTrue(response.getBody().contains("Date desc"));
        Assertions.assertTrue(response.getBody().contains("Place none"));

        Assertions.assertTrue(response.getBody().contains("?sortBy=date&amp;sortOrder=none"));
    }

    @Test
    public void category_sort_by_place_asc() {
        String url = "http://localhost:" + port + "/category/" + RECHT_NR + "?sortBy=place&sortOrder=asc";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertTrue(response.getBody().contains("Name none"));
        Assertions.assertTrue(response.getBody().contains("Date none"));
        Assertions.assertTrue(response.getBody().contains("Place asc"));

        Assertions.assertTrue(response.getBody().contains("?sortBy=place&amp;sortOrder=desc"));
    }
    @Test
    public void category_sort_by_place_desc() {
        String url = "http://localhost:" + port + "/category/" + RECHT_NR + "?sortBy=place&sortOrder=desc";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertTrue(response.getBody().contains("Name none"));
        Assertions.assertTrue(response.getBody().contains("Date none"));
        Assertions.assertTrue(response.getBody().contains("Place desc"));

        Assertions.assertTrue(response.getBody().contains("?sortBy=place&amp;sortOrder=none"));
    }


    @Test
    public void category_invalid_sortBy() {
        String url = "http://localhost:" + port + "/category/" + RECHT_NR + "?sortBy=invalid&sortOrder=asc";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());

        Assertions.assertTrue(response.getBody().contains("Name none"));
        Assertions.assertTrue(response.getBody().contains("Date none"));
        Assertions.assertTrue(response.getBody().contains("Place none"));

    }

    @Test
    public void category_invalid_sortOrder() {
        String url = "http://localhost:" + port + "/category/" + RECHT_NR + "?sortBy=name&sortOrder=invalid";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());

        Assertions.assertTrue(response.getBody().contains("Name none"));
        Assertions.assertTrue(response.getBody().contains("Date none"));
        Assertions.assertTrue(response.getBody().contains("Place none"));

    }
}
