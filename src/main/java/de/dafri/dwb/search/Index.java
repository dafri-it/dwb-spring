package de.dafri.dwb.search;

import de.dafri.dwb.util.Slugger;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Index<T> {

    private final Map<String, T> map = new ConcurrentHashMap<>();

    public void put(String key, T value) {
        map.put(Slugger.slug(key), value);
    }

    public List<T> search(String query) {
        List<RankedSearchResult<T>> results = rankedSearch(query);

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
                .map(e ->searchInParts(e, parts))
                .filter(r ->r.score() > 0)
                .sorted((o1, o2) -> (int) (o2.score() * 100 - o1.score() * 100))
                .toList();
    }

    private RankedSearchResult<T> searchInParts(Map.Entry<String, T> e, String[] parts) {
        double score = 0;
        for (String part : parts) {
            RankedSearchResult<T> result = searchInString(e, part);
            score += result.score();
        }
        return new RankedSearchResult<>(e.getValue(), score);
    }

    private RankedSearchResult<T> searchInString(Map.Entry<String, T> e, String query) {
        if (e.getKey().equals(query)) {
            return new RankedSearchResult<>(e.getValue(), 1);
        }

        if (e.getKey().startsWith(query)) {
            return new RankedSearchResult<>(e.getValue(), 0.9);
        }

        if (e.getKey().contains(query)) {
            return new RankedSearchResult<>(e.getValue(), 0.8);
        }

        int score = fuzzyScore(e.getKey(), query);
        if (score > 0) {
            return new RankedSearchResult<>(e.getValue(), score * 0.01);
        }

        return new RankedSearchResult<>(e.getValue(), 0);
    }

    // source https://commons.apache.org/sandbox/commons-text/jacoco/org.apache.commons.text.similarity/FuzzyScore.java.html
    private int fuzzyScore(final CharSequence term, final CharSequence query) {
        final String termLowerCase = term.toString().toLowerCase();
        final String queryLowerCase = query.toString().toLowerCase();

        // the resulting score
        int score = 0;

        // the position in the term which will be scanned next for potential
        // query character matches
        int termIndex = 0;

        // index of the previously matched character in the term
        int previousMatchingCharacterIndex = Integer.MIN_VALUE;

        for (int queryIndex = 0; queryIndex < queryLowerCase.length(); queryIndex++) {
            final char queryChar = queryLowerCase.charAt(queryIndex);

            boolean termCharacterMatchFound = false;
            for (; termIndex < termLowerCase.length()
                    && !termCharacterMatchFound; termIndex++) {
                final char termChar = termLowerCase.charAt(termIndex);

                if (queryChar == termChar) {
                    // simple character matches result in one point
                    score++;

                    // subsequent character matches further improve
                    // the score.
                    if (previousMatchingCharacterIndex + 1 == termIndex) {
                        score += 2;
                    }

                    previousMatchingCharacterIndex = termIndex;

                    // we can leave the nested loop. Every character in the
                    // query can match at most one character in the term.
                    termCharacterMatchFound = true;
                }
            }
        }

        return score;
    }
}
