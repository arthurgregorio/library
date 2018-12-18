package br.eti.arthurgregorio.library.domain.repositories.configuration;

import br.eti.arthurgregorio.library.domain.model.entities.configuration.Group;
import br.eti.arthurgregorio.library.domain.model.entities.configuration.Group_;
import br.eti.arthurgregorio.library.domain.repositories.DefaultRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.Criteria;

import javax.persistence.metamodel.SingularAttribute;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * The user groups repository
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 28/12/2017
 */
@Repository
public interface GroupRepository extends DefaultRepository<Group> {

    /**
     * Find a {@link Group} by the name
     *
     * @param name the name of the {@link Group} to search
     * @return an {@link Optional} of the {@link Group}
     */
    Optional<Group> findOptionalByName(String name);

    /**
     * {@inheritDoc }
     * 
     * @param filter
     * @return 
     */
    @Override
    default Collection<Criteria<Group, Group>> getRestrictions(String filter) {
        return List.of(this.criteria().likeIgnoreCase(Group_.name, this.likeAny(filter)));
    }
    
    /**
     * {@inheritDoc }
     * 
     * @return 
     */
    @Override
    default SingularAttribute<Group, Boolean> getEntityStateProperty() {
        return Group_.active;
    }
    
    /**
     * {@inheritDoc }
     * 
     * @param criteria 
     */
    @Override
    default void setOrder(Criteria<Group, Group> criteria) {
        criteria.orderAsc(Group_.name);
    }
}
