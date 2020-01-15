package br.eti.arthurgregorio.library.infrastructure.deltaspike.features;

import br.eti.arthurgregorio.library.domain.entities.PersistentEntity;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;

import java.util.List;
import java.util.Optional;

/**
 * Default implementation for a entity repository
 *
 * @param <T> type that this repository must support, must extend {@link PersistentEntity}
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 2.2.0, 15/01/2020
 */
public interface DefaultFeatures<T extends PersistentEntity> extends EntityRepository<T, Long>, CriteriaSupport<T> {

    /**
     * Generic method to find a entity by Id
     *
     * @param id the id to search
     * @return the entity in a optional state
     */
    Optional<T> findById(Long id);

    /**
     * Generic method to find all active entities
     *
     * @return the {@link List} of all active entities
     */
    List<T> findAllActive();

    /**
     * Generic method to find all inactive entities
     *
     * @return the {@link List} of all active entities
     */
    List<T> findAllInactive();

    /**
     * Generic method to find by a given {@link String} filter and by the entity {@link Boolean} state
     *
     * @param filter the value to be used as a filter
     * @param active entity state, should be a boole value representing active or inactive state
     * @return {@link List} with the values found
     */
    List<T> findAllBy(String filter, Boolean active);

    /**
     * Helper method to make a simple LIKE clause look in both ways (begin and end) of the sentence.
     *
     * Example: if the filter is 'John' the result after calling this method should be '%John%'
     *
     * @param filter the filter to put the wildcard '%'
     * @return the string filter with 'any' style
     */
    default String likeAny(String filter) {
        return "%" + filter + "%";
    }
}