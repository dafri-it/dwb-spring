package de.dafri.dwb.search;

public class FuzzySearcher {

    public static double fuzzySearch(String term, String query) {
        char[] termChars = term.toCharArray();
        char[] queryChars = query.toCharArray();

        double score = 0.0;
        double fullScore = Math.max(termChars.length, queryChars.length);
        for (int i = 0; i < termChars.length; i++) {
            {
                double scoreI = compare(termChars, queryChars, i, i, 1.0);
                if (scoreI > 0.0) {
                    score += scoreI;
                    continue;
                }
            }

            {
                double scoreI = compare(termChars, queryChars, i, i-1, 0.9);
                if (scoreI > 0.0) {
                    score += scoreI;
                    continue;
                }
            }
            {
                double scoreI = compare(termChars, queryChars, i, i+1, 0.9);
                if (scoreI > 0.0) {
                    score += scoreI;
                    continue;
                }
            }

            {
                double scoreI = compare(termChars, queryChars, i, i-2, 0.8);
                if (scoreI > 0.0) {
                    score += scoreI;
                    continue;
                }
            }
            {
                double scoreI = compare(termChars, queryChars, i, i+2, 0.8);
                if (scoreI > 0.0) {
                    score += scoreI;
//                    continue;
                }
            }
        }

        if (score == 0) {
            return 0.0;
        }

        return score / fullScore;
    }

    private static double compare(char[] termChars, char[] queryChars, int indexTerm, int indexQuery, double foundScore) {
        if (indexTerm < 0 || indexTerm >= termChars.length) {
            return 0.0;
        }
        if (indexQuery < 0 || indexQuery >= queryChars.length) {
            return 0.0;
        }

        if (termChars[indexTerm] == queryChars[indexQuery]) {
            return foundScore;
        }

        return 0.0;
    }
}
