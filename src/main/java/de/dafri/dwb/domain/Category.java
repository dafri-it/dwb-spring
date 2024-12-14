package de.dafri.dwb.domain;

import java.util.List;

public record Category(Long id, String nr, String name, String description, List<Category> children) {
}
