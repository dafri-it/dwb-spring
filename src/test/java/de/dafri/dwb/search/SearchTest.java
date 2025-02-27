package de.dafri.dwb.search;

import de.dafri.dwb.domain.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SearchTest {

    @Autowired
    private Search search;

    @Test
    public void search_Category_aktuell_finds_Aktuelles() {
        searchCategory("aktuell", "Aktuelles");
    }

    @Test
    public void search_Category_recht_finds_Recht() {
        searchCategory("recht", "Recht");
    }

    @Test
    public void search_Category_beamten_finds_Beamtenrecht() {
        searchCategory("beamten", "Beamtenrecht");
    }

    @Test
    public void search_Category_schutz_finds_Datenschutz() {
        searchCategory("datenschu", "Datenschutz / Personalaktenrecht");
    }

    @Test
    public void search_Category_datenschtz_finds_Datenschutz() {
        searchCategory("datenschtz", "Datenschutz / Personalaktenrecht");
    }

    @Test
    public void search_Category_junk_finds_nothing() {
        List<Category> results = search.searchCategory("qwert");
        assertNotNull(results);
        assertTrue(results.isEmpty());
    }

    @Test
    public void search_Category_arbeitsrecht_finds() {
        searchCategory("arbeitsrecht öffenltich", "Arbeitsrecht / Personalmanagement (öffentlicher Dienst)");
    }


    @Test
    public void search_Category_arbeitsrecht_slug_finds() {
        searchCategory("arbeitsrecht-oeffenltich", "Arbeitsrecht / Personalmanagement (öffentlicher Dienst)");
    }

    private void searchCategory(String query, String expected) {
        List<Category> results = search.searchCategory(query);
        assertNotNull(results);
        assertFalse(results.isEmpty());
        assertEquals(expected, results.getFirst().name(), results.toString());
    }

}