package br.eti.arthurgregorio.library.application.components.table;

import br.eti.arthurgregorio.library.domain.model.entities.PersistentEntity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 *
 * @param <T>
 * 
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.2.1, 22/04/2018
 */
public class Page<T extends PersistentEntity> {

    public Optional<List<T>> optionalContent;
    public Optional<Long> optionalTotalPages;

    /**
     * 
     * @param content
     * @param totalPages 
     */
    private Page(List<T> content, Long totalPages) {
        this.optionalContent = Optional.of(content);
        this.optionalTotalPages = Optional.of(totalPages);
    }
    
    /**
     * 
     * @return 
     */
    public static Page empty() {
        return new Page<>(Collections.emptyList(), 0L);
    }
    
    /**
     * 
     * @param <V>
     * @param content
     * @param totalPages
     * @return 
     */
    public static <V extends PersistentEntity> Page<V> of(List<V> content, Long totalPages) {
        return new Page<>(content, totalPages);
    }
    
    /**
     * 
     * @return 
     */
    public List<T> getContent() {
        return this.optionalContent.orElse(new ArrayList<>());
    }

    /**
     * 
     * @param content 
     */
    public void setContent(List<T> content) {
        this.optionalContent = Optional.of(content);
    }

    /**
     * 
     * @return 
     */
    public Long getTotalPages() {
        return this.optionalTotalPages.orElse(0L);
    }
    
    /**
     * 
     * @return 
     */
    public int getTotalPagesInt() {
        return this.optionalTotalPages.orElse(0L).intValue();
    }

    /**
     * 
     * @param totalPages 
     */
    public void setTotalPages(Long totalPages) {
        this.optionalTotalPages = Optional.of(totalPages);
    }
}
