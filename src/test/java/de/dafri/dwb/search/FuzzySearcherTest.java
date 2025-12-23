package de.dafri.dwb.search;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FuzzySearcherTest {


    @Test
    public void beamt_beamten() {
        search("beamten", "beamten", 1.0, 1.0);
    }

    @Test
    public void nomatch() {
        search("abc", "dfr", 0.0, 0.0);
    }

    @Test
    public void arbeit() {
        search("arbeit", "abet", 0.3, 0.7);
    }

    @Test
    public void recht() {
        search("recht", "ercht", 0.5, 1.0);
    }

    @Test
    public void recht_echt() {
        search("recht", "rcht", 0.5, 0.8);
    }

    @Test
    public void datenschtz_datenschutz() {
        search("datenschutz", "datenschtz", 0.5, 0.9);
    }
    @Test
    public void beamtenrecht() {
        search("beamtenrecht", "beamtenrcht", 0.5, 0.9);
    }


    private void search(String term, String query, double minScore, double maxScore) {
        double score = FuzzySearcher.fuzzySearch(term, query);

        System.out.println(score);
        assertTrue(score >= minScore, "score=" + score + " minScore=" + minScore);
        assertTrue(score <= maxScore,  "score=" + score + " maxScore=" + maxScore);
    }

}