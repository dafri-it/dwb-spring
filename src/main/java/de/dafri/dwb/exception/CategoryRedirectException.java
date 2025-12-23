package de.dafri.dwb.exception;

import de.dafri.dwb.domain.Category;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FOUND)
public class CategoryRedirectException extends RuntimeException {

    private final Category category;
    private final boolean api;

    public CategoryRedirectException(Category category, boolean api) {
        this.category = category;
        this.api = api;
    }

    public Category getCategory() {
        return category;
    }

    public boolean isApi() {
        return api;
    }
}
