package de.dafri.dwb.view;

public record SortToggle(String sortBy, String sortOrder) {

    public final static String ASCENDING = "asc";
    public final static String DESCENDING = "desc";
    public final static String NONE = "none";

    public SortToggle by(String name) {
        if (this.sortBy.equals(name)) {
            if (ASCENDING.equals(this.sortOrder)) {
                return new SortToggle(this.sortBy, DESCENDING);
            }
            else if (DESCENDING.equals(this.sortOrder)) {
                return new SortToggle(this.sortBy, NONE);
            } else {
                return new SortToggle(this.sortBy, ASCENDING);
            }
        } else {
            return new SortToggle(name, ASCENDING);
        }
    }
}
