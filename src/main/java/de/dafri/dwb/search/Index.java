package de.dafri.dwb.search;

import de.dafri.dwb.util.Slugger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Index<T> {

    private static final Logger logger = LoggerFactory.getLogger(Index.class);

    private final Map<String, T> map = new ConcurrentHashMap<>();

    public void put(String key, T value) {
        map.put(Slugger.slug(key), value);
    }

    public List<T> search(String query) {
        List<RankedSearchResult<T>> results = rankedSearch(query);

        results.forEach(r -> logger.debug(r.toString()));

        return results.stream()
                .map(RankedSearchResult::result)
                .toList();
    }

    public List<RankedSearchResult<T>> rankedSearch(String query) {
        if (query == null || query.isEmpty()) {
            return null;
        }

        String[] parts = Slugger.slug(query).split("-");

        return map.entrySet().stream()
                .map(e -> searchInParts(e, parts))
                .filter(r -> r.score() > 0.6)
                .sorted(Comparator.comparingDouble(RankedSearchResult::score))
                .toList()
                .reversed();
    }

    private RankedSearchResult<T> searchInParts(Map.Entry<String, T> e, String[] parts) {
        double score = 0;
        for (int i = 0; i < parts.length; i++) {
            String part = parts[i];
            String[] termParts = Slugger.slug(e.getKey()).split("-");
            for (int j = 0; j < termParts.length; j++) {
                String termPart = termParts[j];
                score += searchInString(termPart, part);
                if (i == j && score > 0.6) {
                    score += 1.0;
                }
            }
        }
        return new RankedSearchResult<>(e.getValue(), score);
    }

    private double searchInString(String term, String query) {
        if (term.length() < 3 || query.length() < 3) {
            return 0;
        }

        if (term.equals(query)) {
            return 1;
        }

        if (term.startsWith(query)) {
            return 0.9;
        }

        if (term.contains(query)) {
            return 0.8;
        }

        double score = FuzzySearcher.fuzzySearch(term, query);
        if (score > 0.7) {
            return 0.7;
        }

        return -0.01;
    }

}
