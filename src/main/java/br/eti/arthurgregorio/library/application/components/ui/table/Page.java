package br.eti.arthurgregorio.library.application.components.ui.table;

import br.eti.arthurgregorio.library.domain.entities.PersistentEntity;
import lombok.Getter;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * This class is a basic representation of the data to be provided to the data table component from Primefaces.
 *
 * In this we ship all the metadata to the pagination work 
 *
 * @param <T> the generic type of the page
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 22/04/2018
 */
public final class Page<T extends PersistentEntity> {

    @Getter
    public final List<T> content;
    @Getter
    public final long totalPages;

    /**
     * Create a new page
     *
     * @param content the content
     * @param totalPages the total of possible pages
     */
    private Page(List<T> content, long totalPages) {
        this.content = Objects.requireNonNull(content);
        this.totalPages = totalPages;
    }

    /**
     * Create a page with empty content
     *
     * @return the empty page
     */
    public static <V extends PersistentEntity> Page<V> empty() {
        return new Page<>(Collections.emptyList(), 0);
    }

    /**
     * Create a new page of a given content and with the count of total pages
     *
     * @param <V> the generic type of the page
     * @param content the content
     * @param totalPages the total count of pages
     * @return the page with the given content
     */
    public static <V extends PersistentEntity> Page<V> of(List<V> content, long totalPages) {
        return new Page<>(content, totalPages);
    }
}
