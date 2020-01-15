package br.eti.arthurgregorio.library.infrastructure.deltaspike.extensions;

import br.eti.arthurgregorio.library.domain.entities.PersistentEntity;
import br.eti.arthurgregorio.library.infrastructure.deltaspike.features.QueryDSLSupport;
import com.querydsl.jpa.impl.JPAQuery;
import org.apache.deltaspike.data.spi.DelegateQueryHandler;
import org.apache.deltaspike.data.spi.QueryInvocationContext;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

/**
 * Extension to support QueryDSL framework
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 09/01/2020
 */
@Dependent
public class QueryDSLExtension<T extends PersistentEntity> implements DelegateQueryHandler, QueryDSLSupport<T> {

    @Inject
    private QueryInvocationContext context;

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public JPAQuery<T> jpaQuery() {
        return new JPAQuery<>(this.context.getEntityManager());
    }
}
