package br.eti.arthurgregorio.library.infrastructure.deltaspike;

import br.eti.arthurgregorio.library.domain.entities.PersistentEntity;
import com.querydsl.jpa.impl.JPAQuery;

/**
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 09/01/2020
 */
public interface QueryDSLSupport<T extends PersistentEntity> {

    /**
     *
     * @return
     */
    JPAQuery<T> jpaQuery();
}
