package de.dafri.dwb.exception;

import de.dafri.dwb.domain.Category;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FOUND)
public class CategoryRedirectException extends RuntimeException {

    private final Category category;

    public CategoryRedirectException(Category category) {
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }
}
