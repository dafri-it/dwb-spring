package de.dafri.dwb.search;

import jakarta.annotation.PostConstruct;

import java.util.List;
import java.util.function.Supplier;

abstract class Search<T> {

    private final Index<T> index = new Index<>();

    public List<T> search(String query) {
        return index.search(query);
    }

    protected abstract Supplier<List<T>> getDataSupplier();

    @PostConstruct
    protected void init() {
        addElements(index);
    }

    private void addElements(Index<T> index) {
        List<T> elements = getDataSupplier().get();
        for (T element : elements) {
            index.put(getElementKey(element), element);
        }
    }

    protected abstract String getElementKey(T element);

}
