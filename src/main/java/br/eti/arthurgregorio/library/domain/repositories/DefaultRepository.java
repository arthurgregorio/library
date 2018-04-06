package br.eti.arthurgregorio.library.domain.repositories;

import br.eti.arthurgregorio.library.domain.model.entities.PersistentEntity;
import br.eti.arthurgregorio.library.domain.model.entities.PersistentEntity_;
import java.util.List;
import java.util.Optional;
import javax.persistence.metamodel.SingularAttribute;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.criteria.Criteria;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;

/**
 * The default implementation of a repository in the application
 * 
 * Every repository should extend this class to get some features that are not
 * default in the basic Deltaspike implementation
 *
 * @param <T> the type of
 * 
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 21/03/2018
 */
public interface DefaultRepository<T extends PersistentEntity> extends EntityRepository<T, Long>, CriteriaSupport<T> {

    /**
     * Generic method to find a entity by Id
     * 
     * @param id the id to search
     * @return the entity in a optional state
     */
    Optional<T> findOptionalById(Long id);
    
    /**
     * Generic search method
     * 
     * To use this method you need to implement {@link #getRestrictions(java.lang.String)}
     * and {@link #getBlockedProperty()}
     * 
     * @param filter the string filter to use
     * @param blocked the object status of the entity, null means all states
     * @param start the start page
     * @param pageSize the size of the page
     * @return the list of objects found
     */
    default List<T> findAllBy(String filter, Boolean blocked, int start, int pageSize) {
        
        final Criteria<T, T> criteria = criteria();
        
        if (isNotBlank(filter)) {
            criteria.or(this.getRestrictions(filter));
        } 
        
        if (blocked != null) {
            criteria.eq(this.getBlockedProperty(), blocked);
        }
                
        criteria.orderDesc(PersistentEntity_.id);
        
        return criteria.createQuery()
                .setFirstResult(start)
                .setMaxResults(pageSize)
                .getResultList();
    }

    /**
     * Generic method to find all unblocked entities
     * 
     * @return a list of all unblocked entities
     */
    default List<T> findAllUnblocked() {
        return criteria()
                .eq(this.getBlockedProperty(), false)
                .orderDesc(PersistentEntity_.id)
                .getResultList();
    }

    /**
     * This method shoud be implemented if the user needs to use the generic 
     * type search with the {@link #findAllBy(java.lang.String, java.lang.Boolean, int, int)}
     * method
     * 
     * With this we can detect all the restrictions to build the criteria 
     * 
     * @param filter the generic filter in {@link String} format
     * @return the criteria for the type of the repository
     */
    default Criteria<T, T> getRestrictions(String filter) {
        throw new RuntimeException("getRestrictions not implemented for query");
    }
    
    /**
     * This method shoud be implemented if the user needs to use the generic 
     * type search with the {@link #findAllBy(java.lang.String, java.lang.Boolean, int, int)}
     * method
     * 
     * @return the attribute responsible for representing the blocked property
     * of the entity
     */
    default SingularAttribute<T, Boolean> getBlockedProperty() {
        throw new RuntimeException("getBlockProperty not implemented for query");
    }
}