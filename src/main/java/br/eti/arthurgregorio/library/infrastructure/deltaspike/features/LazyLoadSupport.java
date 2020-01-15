package br.eti.arthurgregorio.library.infrastructure.deltaspike.features;

import br.eti.arthurgregorio.library.application.components.ui.table.Page;
import br.eti.arthurgregorio.library.domain.entities.PersistentEntity;

import java.util.List;

/**
 * {@link DefaultFeatures} implementation with lazy load support
 *
 * @param <T> type of this repository
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 2.2.0, 15/01/2020
 */
public interface LazyLoadSupport<T extends PersistentEntity> extends DefaultFeatures<T> {

    /**
     * Method used to count the amount of pages the query will create
     *
     * @param filter the value to be used as a filter
     * @param active entity state, should be a boole value representing active or inactive state
     * @return total of pages represented by the given filter and state
     */
    long countPages(String filter, Boolean active);

    /**
     * This method has the same proposal of {@link #findAllBy(String, Boolean)} but this one takes two more parameters
     * to provide lazy load support
     *
     * @param filter the value to be used as a filter
     * @param active entity state, should be a boole value representing active or inactive state
     * @param startingAt the starting point to limit the page
     * @param pageSize total rows of the page
     * @return {@link List} with the values found
     */
    Page<T> findAllBy(String filter, Boolean active, int startingAt, int pageSize);
}