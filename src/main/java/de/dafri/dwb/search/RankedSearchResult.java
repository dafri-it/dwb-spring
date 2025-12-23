package de.dafri.dwb.search;

public record RankedSearchResult<T>(T result, double score) {
}
