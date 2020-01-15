package br.eti.arthurgregorio.library.infrastructure.deltaspike.repositories;

import br.eti.arthurgregorio.library.application.components.ui.table.Page;
import br.eti.arthurgregorio.library.domain.entities.PersistentEntity;
import br.eti.arthurgregorio.library.domain.entities.PersistentEntity_;
import br.eti.arthurgregorio.library.infrastructure.deltaspike.features.LazyLoadSupport;
import org.apache.deltaspike.data.api.criteria.Criteria;

import javax.persistence.metamodel.SingularAttribute;
import java.util.Collection;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * Delta Spike Data Module repository implementation
 *
 * @param <T> type that this repository must support, must extend {@link PersistentEntity}
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 2.2.0, 15/01/2020
 */
public interface DeltaSpikeRepository<T extends PersistentEntity> extends LazyLoadSupport<T> {

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    default List<T> findAllInactive() {

        final Criteria<T, T> criteria = criteria()
                .eq(this.getEntityStateProperty(), false);

        this.applyOrder(criteria);

        return criteria.getResultList();
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    default List<T> findAllActive() {

        final Criteria<T, T> criteria = criteria()
                .eq(this.getEntityStateProperty(), true);

        this.applyOrder(criteria);

        return criteria.getResultList();
    }

    /**
     * {@inheritDoc}
     *
     * @param filter
     * @param active
     * @return
     */
    @Override
    default List<T> findAllBy(String filter, Boolean active) {

        final Criteria<T, T> criteria = this.applyFilters(filter, active);

        this.applyOrder(criteria);

        return criteria.createQuery().getResultList();
    }

    /**
     * {@inheritDoc}
     *
     * @param filter
     * @param active
     * @param startingAt
     * @param pageSize
     * @return
     */
    @Override
    default Page<T> findAllBy(String filter, Boolean active, int startingAt, int pageSize) {

        final long totalRows = this.countPages(filter, active);

        final Criteria<T, T> criteria = this.applyFilters(filter, active);

        this.applyOrder(criteria);

        final List<T> data = criteria.createQuery()
                .setFirstResult(startingAt)
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
    @Override
    @SuppressWarnings("unchecked")
    default long countPages(String filter, Boolean active) {
        return this.applyFilters(filter, active)
                .select(Long.class, count(PersistentEntity_.id))
                .getSingleResult();
    }

    /**
     * Method used to apply the filters to the current query
     *
     * @param filter to be applied
     * @param active entity state indicating active or inactive (true or false)
     * @return {@link List} of all {@link Criteria} to be used as query filters
     */
    default Criteria<T, T> applyFilters(String filter, Boolean active) {

        final Criteria<T, T> criteria = criteria();

        if (isNotBlank(filter)) {
            criteria.or(this.getRestrictions(filter));
        }

        if (active != null) {
            criteria.eq(this.getEntityStateProperty(), active);
        }

        return criteria;
    }

    /**
     * Method used to apply the order to the query
     *
     * @param criteria to be ordered
     */
    default void applyOrder(Criteria<T, T> criteria) {
        criteria.orderAsc(PersistentEntity_.id);
    }

    /**
     * Method used as hook method to get the {@link Collection} of {@link Criteria} to be used as query filter
     *
     * @param filter to be applied to the {@link Criteria}
     * @return {@link Collection} of {@link Criteria}
     */
    default Collection<Criteria<T, T>> getRestrictions(String filter) {
        throw new RuntimeException("getRestrictions not implemented for query");
    }

    /**
     * Hook method used to get the property to represent the state of the entity
     *
     * @return meta-model property used as entity state
     */
    default SingularAttribute<? super T, Boolean> getEntityStateProperty() {
        throw new RuntimeException("getBlockProperty not implemented for query");
    }
}