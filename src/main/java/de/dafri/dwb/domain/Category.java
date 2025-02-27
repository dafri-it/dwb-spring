package de.dafri.dwb.domain;

import de.dafri.dwb.util.Slugger;

import java.util.List;

public record Category(Long id, String nr, String name, String description, List<Category> children) {

    public String slug() {
        return Slugger.slug(name);
    }

}
