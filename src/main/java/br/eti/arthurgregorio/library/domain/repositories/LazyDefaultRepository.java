package br.eti.arthurgregorio.library.domain.repositories;

import br.eti.arthurgregorio.library.application.components.table.Page;
import br.eti.arthurgregorio.library.domain.entities.PersistentEntity;
import br.eti.arthurgregorio.library.domain.entities.PersistentEntity_;
import org.apache.deltaspike.data.api.criteria.Criteria;

import java.util.List;

/**
 * Implementation for {@link DefaultRepository} with lazy load support
 *
 * @param <T> the type of this repository
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 2.1.0, 23/05/2019
 */
public interface LazyDefaultRepository<T extends PersistentEntity> extends DefaultRepository<T> {

    /**
     * Generic search method with lazy pagination support. To use this method you must implement
     * {@link #getRestrictions(String)} and {@link #getEntityStateProperty()}
     *
     * @param filter the filter to be used to find the objects
     * @param active the object state in the database, null means all states
     * @param start the starting page
     * @param pageSize size of the page
     * @return {@link Page} filled with the objects found
     */
    default Page<T> findAllBy(String filter, Boolean active, int start, int pageSize) {

        final int totalRows = this.countPages(filter, active);

        final Criteria<T, T> criteria = this.buildCriteria(filter, active);

        this.setOrder(criteria);

        final List<T> data = criteria.createQuery()
                .setFirstResult(start)
                .setMaxResults(pageSize)
                .getResultList();

        return Page.of(data, totalRows);
    }

    /**
     * Count the pages for pagination purpose
     *
     * @param filter the filter to use in count process
     * @param active if consider only active or inactive entities
     * @return the total of pages
     */
    @SuppressWarnings("unchecked")
    default int countPages(String filter, Boolean active) {
        return this.buildCriteria(filter, active)
                .select(Long.class, count(PersistentEntity_.id))
                .getSingleResult()
                .intValue();
    }
}