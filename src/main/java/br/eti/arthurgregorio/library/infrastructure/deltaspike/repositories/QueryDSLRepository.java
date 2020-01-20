package br.eti.arthurgregorio.library.infrastructure.deltaspike.repositories;

import br.eti.arthurgregorio.library.application.components.ui.table.Page;
import br.eti.arthurgregorio.library.domain.entities.PersistentEntity;
import br.eti.arthurgregorio.library.infrastructure.deltaspike.features.LazyLoadSupport;
import br.eti.arthurgregorio.library.infrastructure.deltaspike.features.QueryDSLSupport;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanPath;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.jpa.impl.JPAQuery;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * DeltaSpike base repository for QueryDSL support
 *
 * @param <T> type that this repository must support, must extend {@link PersistentEntity}
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 2.2.0, 15/01/2020
 */
public interface QueryDSLRepository<T extends PersistentEntity> extends QueryDSLSupport<T>, LazyLoadSupport<T> {

    /**
     * {@inheritDoc}
     *
     * @return
     */
    default List<T> findAllInactive() {
        return this.jpaQueryFor(this.getQType())
                .where(this.getQStateProperty().eq(false))
                .orderBy(this.getQOrders())
                .fetch();
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    default List<T> findAllActive() {
        return this.jpaQueryFor(this.getQType())
                .where(this.getQStateProperty().eq(true))
                .orderBy(this.getQOrders())
                .fetch();
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
        return this.jpaQueryFor(this.getQType())
                .where(this.applyPredicates(filter, active))
                .orderBy(this.getQOrders())
                .fetch();
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

        final QueryResults<T> results = this.jpaQueryFor(this.getQType())
                .where(this.applyPredicates(filter, active))
                .orderBy(this.getQOrders())
                .offset(startingAt)
                .limit(pageSize)
                .fetchResults();

        final long pageCount = results.getTotal();

        return Page.of(results.getResults(), pageCount);
    }

    /**
     * Method used to apply the predicates defined by the method {@link #getQPredicate(String)} into the query
     *
     * @param filter used to create the predicate condition
     * @param active entity state used to create the active/inactive condition
     * @return an array of {@link Predicate}
     */
    default Predicate[] applyPredicates(String filter, Boolean active) {

        final List<Predicate> predicates = new ArrayList<>();

        if (isNotBlank(filter)) {
            predicates.add(this.getQPredicate(filter));
        }

        if (active != null) {
            predicates.add(this.getQStateProperty().eq(active));
        }

        return predicates.toArray(Predicate[]::new);
    }

    /**
     * Create a new root query using {@link #jpaQuery()}
     *
     * @param entityPath represents the entity type to be used on the FROM clause in the SQL query
     * @param <V> value type of this {@link EntityPath}
     * @return a new {@link JPAQuery} typed by the {@link EntityPath}
     */
    default <V> JPAQuery<T> jpaQueryFor(EntityPath<V> entityPath) {
        return this.jpaQuery().from(entityPath);
    }

    /**
     * Get the property used to define which state of the entity we are going to query
     *
     * @return a {@link BooleanPath} representing the state of the entity
     */
    default BooleanPath getQStateProperty() {
        throw new RuntimeException("getQStateProperty not implemented for query");
    }

    /**
     * Get the {@link EntityPathBase} to be used as root object for this repository
     *
     * @return an {@link EntityPathBase} with the same type of this interface
     */
    default EntityPathBase<T> getQType() {
        throw new RuntimeException("getQType not implemented for query");
    }

    /**
     * Return the chain of predicates to be used as filter
     *
     * @param filter string value to be used as filter with the predicates
     * @return a single chain of {@link Predicate}
     */
    default Predicate getQPredicate(String filter) {
        throw new RuntimeException("getQPredicate not implemented for query");
    }

    /**
     * Helper method used to define the array of possible orders to be applied
     *
     * @return a {@link List} of {@link OrderSpecifier}
     */
    default OrderSpecifier<?>[] getQOrders() {
        throw new RuntimeException("getQOrders not implemented for query");
    }
}
