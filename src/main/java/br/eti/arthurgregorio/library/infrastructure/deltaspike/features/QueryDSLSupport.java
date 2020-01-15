package br.eti.arthurgregorio.library.infrastructure.deltaspike.features;

import br.eti.arthurgregorio.library.domain.entities.PersistentEntity;
import com.querydsl.jpa.impl.JPAQuery;

/**
 * DeltaSpike extension to support
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 09/01/2020
 */
public interface QueryDSLSupport<T extends PersistentEntity> {

    /**
     * Create a new QueryDSL {@link JPAQuery}
     *
     * @return {@link JPAQuery} object
     */
    JPAQuery<T> jpaQuery();
}
